package kz.afm.candidate.test.session;

import kz.afm.candidate.dto.ResponseBodyWrapper;
import kz.afm.candidate.test.TestService;
import kz.afm.candidate.test.question.QuestionService;
import kz.afm.candidate.test.session.dto.CreateTestSessionResponse;
import kz.afm.candidate.test.session.dto.TestSessionAnswerRequest;
import kz.afm.candidate.test.session.dto.TestSessionForAssessmentResponse;
import kz.afm.candidate.test.session.dto.TestSessionListForAssessmentResponse;
import kz.afm.candidate.test.test_type.point_distribution.PointDistributionTestService;
import kz.afm.candidate.test.variant.VariantEntity;
import kz.afm.candidate.test.variant.VariantService;
import kz.afm.candidate.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<ResponseBodyWrapper<CreateTestSessionResponse>> createAndSend(
            @AuthenticationPrincipal UserEntity requestingUser,
            @RequestParam long testId
    ) {
        try {
            return ResponseEntity.ok(this.create(requestingUser, testId));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(ResponseBodyWrapper.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

    private ResponseBodyWrapper<CreateTestSessionResponse> create(UserEntity requestingUser, long testId) {
        final int testTypeId = this.testService.getTypeIdByTestId(testId);
        final VariantEntity variant = this.variantService.getRandom(testId);
        final Set<Long> questionIds = this.questionService.getIdsByVariant(variant);
        final long testSessionId = this.testSessionService.createFromUserAndVariant(requestingUser, variant);
        final int maxPointsPerQuestion = this.pointDistributionTestService.getMaxPointsPerQuestionByTestIdAndTestTypeId(testId, testTypeId);

        return ResponseBodyWrapper.success(
                new CreateTestSessionResponse(testSessionId, questionIds, testTypeId, maxPointsPerQuestion)
        );
    }

    @PutMapping("{test_session_id}")
    public ResponseEntity<ResponseBodyWrapper<Void>> end(
            @PathVariable(name = "test_session_id") long testSessionId,
            @RequestBody List<TestSessionAnswerRequest> answers
    ) {
        try {
            this.testSessionService.end(testSessionId, answers);
            return ResponseEntity.ok(ResponseBodyWrapper.success());
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

    @GetMapping("all/assessment")
    public ResponseEntity<ResponseBodyWrapper<TestSessionListForAssessmentResponse>> getAllForAssessment(
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "-1") int pageSize,
            @RequestParam boolean checked,
            @RequestParam(required = false, defaultValue = "-1") int regionId
    ) {
        try {
            final List<TestSessionForAssessmentResponse> testSessionsForAssessment = this.testSessionService
                    .getAllForAssessment(regionId, checked, pageNumber, pageSize)
                    .stream()
                    .map(TestSessionForAssessmentResponse::new)
                    .toList();

            final long count = this.testSessionService.countAllForAssessment(regionId, checked);

            final TestSessionListForAssessmentResponse testSessionListForAssessment =
                    new TestSessionListForAssessmentResponse(count, testSessionsForAssessment);

            return ResponseEntity.ok(ResponseBodyWrapper.success(testSessionListForAssessment));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

}
