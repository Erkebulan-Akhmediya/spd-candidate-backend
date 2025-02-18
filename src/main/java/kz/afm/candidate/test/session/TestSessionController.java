package kz.afm.candidate.test.session;

import kz.afm.candidate.dto.ResponseBodyWrapper;
import kz.afm.candidate.test.TestEntity;
import kz.afm.candidate.test.TestService;
import kz.afm.candidate.test.question.QuestionService;
import kz.afm.candidate.test.session.answer.TestSessionAnswerEntity;
import kz.afm.candidate.test.session.answer.TestSessionAnswerService;
import kz.afm.candidate.test.session.dto.*;
import kz.afm.candidate.test.session.evaluation.assessment.AssessmentEntity;
import kz.afm.candidate.test.session.evaluation.assessment.AssessmentService;
import kz.afm.candidate.test.session.evaluation.result.ResultEntity;
import kz.afm.candidate.test.session.evaluation.result.ResultService;
import kz.afm.candidate.test.session.evaluation.section.SectionEntity;
import kz.afm.candidate.test.session.evaluation.section.SectionService;
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
    private final TestSessionAnswerService testSessionAnswerService;
    private final ResultService resultService;
    private final AssessmentService assessmentService;
    private final SectionService sectionService;

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
            final TestSessionEntity endedTestSession = this.testSessionService.end(testSessionId, answers);
            this.testSessionService.evaluateAfterResponse(endedTestSession);
            return ResponseEntity.ok(ResponseBodyWrapper.success());
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

    @GetMapping("all/{purpose:[a-zA-Z]+}")
    public ResponseEntity<ResponseBodyWrapper<TestSessionListForAssessment>> getAllForAssessment(
            @PathVariable String purpose,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "-1") int pageSize,
            @RequestParam(required = false, defaultValue = "-1") int regionId
    ) {
        try {
            final List<TestSessionDto> testSessionDtoList = this.testSessionService
                    .getAllByPurpose(purpose, regionId, pageNumber, pageSize)
                    .stream()
                    .map(TestSessionDto::new)
                    .toList();

            final long count = this.testSessionService.countAllByRegionId(regionId);

            final TestSessionListForAssessment testSessionListForAssessment =
                    new TestSessionListForAssessment(count, testSessionDtoList);

            return ResponseEntity.ok(ResponseBodyWrapper.success(testSessionListForAssessment));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

    @GetMapping("{test_session_id:\\d+}/assessment")
    public ResponseEntity<ResponseBodyWrapper<TestSessionDto>> getByIdForAssessment(
            @PathVariable(name = "test_session_id") long testSessionId
    ) {
        try {
            final TestSessionEntity testSession = this.testSessionService.getById(testSessionId);
            final List<TestSessionAnswerEntity> answers = this.testSessionAnswerService.getAllByTestSession(testSession);
            final TestSessionDto testSessionDto = new TestSessionDto(testSession, answers);
            return ResponseEntity.ok(ResponseBodyWrapper.success(testSessionDto));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

    @PostMapping("assessment")
    public ResponseEntity<ResponseBodyWrapper<Void>> saveTestSessionAssessment(
            @RequestBody TestSessionDto testSession
    ) {
        try {
            this.testSessionService.assess(testSession.id, testSession.answers);
            return ResponseEntity.ok(ResponseBodyWrapper.success());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

    @GetMapping("{test_session_id:\\d+}/result")
    public ResponseEntity<ResponseBodyWrapper<TestSessionResultDtoList>> getByIdForResult(
            @PathVariable(name = "test_session_id") long testSessionId
    ) {
        try {
            final TestSessionEntity testSession = this.testSessionService.getById(testSessionId);

            final TestEntity test = testSession.getVariant().getTest();
            final String resultType = this.resultService.getType(test);

            final List<TestSessionResultDto> resultDtos;
            if (test.getType().isAutomaticallyEvaluated()) {
                final List<ResultEntity> results = this.resultService.getByTestSession(testSession);
                resultDtos = results.stream()
                        .map(
                                (ResultEntity result) -> {
                                    final SectionEntity section = this.sectionService.getByResult(result);
                                    return new TestSessionResultDto(result, section);
                                }
                        )
                        .toList();
            } else {
                final List<AssessmentEntity> assessments = this.assessmentService.getByTestSession(testSession);
                resultDtos = assessments.stream()
                        .map(TestSessionResultDto::new)
                        .toList();
            }

            final TestSessionResultDtoList resultDtoList = new TestSessionResultDtoList(resultType, resultDtos);

            return ResponseEntity.ok(ResponseBodyWrapper.success(resultDtoList));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

}
