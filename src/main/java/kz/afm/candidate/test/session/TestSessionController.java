package kz.afm.candidate.test.session;

import kz.afm.candidate.dto.ResponseBodyWrapper;
import kz.afm.candidate.test.TestService;
import kz.afm.candidate.test.question.QuestionService;
import kz.afm.candidate.test.session.dto.CreateTestSessionResponse;
import kz.afm.candidate.test.variant.VariantEntity;
import kz.afm.candidate.test.variant.VariantService;
import kz.afm.candidate.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Set;

@RequiredArgsConstructor
@RequestMapping("test/session")
@RestController
public class TestSessionController {

    private final TestSessionService testSessionService;
    private final TestService testService;
    private final VariantService variantService;
    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<ResponseBodyWrapper<CreateTestSessionResponse>> create(
            @AuthenticationPrincipal UserEntity requestingUser,
            @RequestParam long testId
    ) {
        try {
            final int testTypeId = this.testService.getTypeIdByTestId(testId);
            final VariantEntity variant = this.variantService.getRandom(testId);
            final Set<Long> questionIds = this.questionService.getIdsByVariant(variant);
            final long testSessionId = this.testSessionService.create(requestingUser, variant);

            return ResponseEntity.ok(
                    ResponseBodyWrapper.success(
                            new CreateTestSessionResponse(testSessionId, questionIds, testTypeId)
                    )
            );
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(
                    ResponseBodyWrapper.error(e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    ResponseBodyWrapper.error("Ошибка сервера")
            );
        }
    }

}
