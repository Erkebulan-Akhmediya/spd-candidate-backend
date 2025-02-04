package kz.afm.candidate.auth;

import jakarta.validation.Valid;
import kz.afm.candidate.auth.dto.LoginRequest;
import kz.afm.candidate.auth.dto.LoginResponse;
import kz.afm.candidate.candidate.CandidateService;
import kz.afm.candidate.dto.ResponseBodyWrapper;
import kz.afm.candidate.user.UserEntity;
import kz.afm.candidate.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("auth")
@RestController
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthService authService;
    private final CandidateService candidateService;

    @PostMapping("login")
    public ResponseEntity<ResponseBodyWrapper<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        try {
            this.authService.authenticateUser(request.getUsername(), request.getPassword());

            final UserEntity user = this.userService.getByUsername(request.getUsername());

            final boolean isCandidate = this.userService.userIsCandidate(user);
            String areaOfActivity = null;
            if (isCandidate) {
                areaOfActivity = this.candidateService.getCandidatesAreaOfActivityNameByUserId(user.getId());
            }

            final String token = this.jwtService.generateTokenFrom(user);
            return ResponseEntity.ok(ResponseBodyWrapper.success(new LoginResponse(token, areaOfActivity)));

        } catch (AuthenticationException e) {

            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body(ResponseBodyWrapper.error("Ошибка входа"));

        } catch (Exception e) {

            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка на сервере"));

        }
    }

}
