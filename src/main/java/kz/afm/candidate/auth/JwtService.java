package kz.afm.candidate.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import kz.afm.candidate.role.RoleEntity;
import kz.afm.candidate.user.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public UserEntity extractUser(String token) {
        Claims user = extractAllClaims(token);
        List<Map<String, Object>> roles = extractAllClaims(token).get("roles", List.class);

        Set<RoleEntity> roleSet = roles.stream()
                .map(
                        (Map<String, Object> role) -> RoleEntity.builder()
                                .code(role.get("code").toString())
                                .nameKaz(role.get("nameKaz").toString())
                                .nameRus(role.get("nameRus").toString())
                                .build()
                )
                .collect(Collectors.toSet());

        return UserEntity.builder()
                .id(Long.valueOf(user.get("id").toString()))
                .username(user.get("username").toString())
                .password(user.get("password").toString())
                .roles(roleSet)
                .build();
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
