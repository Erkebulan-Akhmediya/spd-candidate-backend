package kz.afm.candidate.test.session;

import kz.afm.candidate.candidate.CandidateService;
import kz.afm.candidate.test.question.QuestionService;
import kz.afm.candidate.test.session.dto.CreateTestSessionResponse;
import kz.afm.candidate.test.variant.VariantEntity;
import kz.afm.candidate.test.variant.VariantService;
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
    private final VariantService variantService;
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<CreateTestSessionResponse> create(
            @AuthenticationPrincipal UserEntity requestingUser,
            @RequestParam long testId
    ) {
        try {
            final VariantEntity variant = this.variantService.getRandom(testId);
            final TestSessionEntity testSession = this.testSessionService.create(
                    this.candidateService.getByUserId(requestingUser.getId()).getIdentificationNumber(),
                    variant
            );
            return ResponseEntity.ok(
                    new CreateTestSessionResponse(
                            testSession.getId(),
                            this.questionService.getCountByVariant(variant)
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

}
