package kz.afm.candidate.test.session;

import kz.afm.candidate.candidate.CandidateService;
import kz.afm.candidate.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("test/session")
@RestController
public class TestSessionController {

    private final TestSessionService testSessionService;
    private final CandidateService candidateService;

    @PostMapping
    public ResponseEntity<Long> create(
            @AuthenticationPrincipal UserEntity requestingUser,
            @RequestParam long testId
    ) {
        try {
            final TestSessionEntity testSession = this.testSessionService.create(
                    this.candidateService.getByUserId(requestingUser.getId()).getIdentificationNumber(),
                    testId
            );
            return ResponseEntity.ok(testSession.getId());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

}
