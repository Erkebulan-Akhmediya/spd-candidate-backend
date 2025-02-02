package kz.afm.candidate.auth;

import jakarta.validation.Valid;
import kz.afm.candidate.auth.dto.LoginRequest;
import kz.afm.candidate.auth.dto.LoginResponse;
import kz.afm.candidate.candidate.CandidateService;
import kz.afm.candidate.dto.ResponseBodyFactory;
import kz.afm.candidate.user.UserEntity;
import kz.afm.candidate.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("auth")
@RestController
public class AuthController {

    private final AuthenticationManager authManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthService authService;
    private final CandidateService candidateService;

    @PostMapping("login")
    public ResponseEntity<ResponseBodyFactory<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        try {

            this.authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            final UserEntity user = this.userService.getByUsername(request.getUsername());
            final String token = this.jwtService.generateToken(
                    this.authService.userToExtraClaims(user),
                    user
            );

            String areaOfActivity = null;
            final boolean isCandidate = user.getRoleCodes().contains("candidate");

            if (isCandidate) {
                areaOfActivity = this.candidateService.getByUserId(user.getId()).getAreaOfActivity().getName();
            }

            return ResponseEntity.ok(ResponseBodyFactory.success(new LoginResponse(token, areaOfActivity)));

        } catch (AuthenticationException e) {

            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(ResponseBodyFactory.error("Ошибка входа"));

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseBodyFactory.error("Ошибка на сервере"));

        }
    }

}
