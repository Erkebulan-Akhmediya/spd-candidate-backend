package kz.afm.candidate.test.session;

import kz.afm.candidate.dto.ResponseBodyWrapper;
import kz.afm.candidate.test.TestService;
import kz.afm.candidate.test.question.QuestionService;
import kz.afm.candidate.test.session.dto.CreateTestSessionResponse;
import kz.afm.candidate.test.test_type.point_distribution.PointDistributionTestService;
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
    private final PointDistributionTestService pointDistributionTestService;

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

            int maxPointsPerQuestion = 0;
            final int POINT_DISTRIBUTION_TEST_TYPE_ID = 5;
            if (testTypeId == POINT_DISTRIBUTION_TEST_TYPE_ID) {
                maxPointsPerQuestion = this.pointDistributionTestService.getMaxPointsPerQuestionByTestId(testId);
            }

            return ResponseEntity.ok(
                    ResponseBodyWrapper.success(
                            new CreateTestSessionResponse(testSessionId, questionIds, testTypeId,maxPointsPerQuestion)
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
