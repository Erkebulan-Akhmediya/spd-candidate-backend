package kz.afm.candidate.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${ACCESS_KEY}")
    private String accessKey;

    @Value("${ISSUERS}")
    private String issuers;

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public UserDetails extractUser(String token) {
        return (UserDetails) this.extractAllClaims(token).get("user");
    }

    private String extractIssuer(String token) {
        return this.extractClaim(token, Claims::getIssuer);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private SecretKey getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(this.accessKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String getRandomIssuer() {
        String[] issuers = this.issuers.split(" ");
        Random random = new Random();
        final int i = random.nextInt(issuers.length);
        return issuers[i];
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .issuer(this.getRandomIssuer())
                .subject(userDetails.getUsername())
                .signWith(this.getSecretKey())
                .compact();
    }

    public boolean isValid(String token) {
        final String issuer = this.extractIssuer(token);
        return Arrays.asList(this.issuers.split(" ")).contains(issuer);
    }

}
