package kz.afm.candidate.test.session;

import jakarta.transaction.Transactional;
import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.candidate.CandidateService;
import kz.afm.candidate.test.session.answer.TestSessionAnswerService;
import kz.afm.candidate.test.session.dto.ConditionalSectioningVariableValueDto;
import kz.afm.candidate.test.session.dto.TestSessionAnswerDto;
import kz.afm.candidate.test.session.dto.TestSessionAnswerRequest;
import kz.afm.candidate.test.session.evaluation.assessment.AssessmentService;
import kz.afm.candidate.test.session.evaluation.result.ResultService;
import kz.afm.candidate.test.session.status.TestSessionStatusEntity;
import kz.afm.candidate.test.session.status.TestSessionStatusService;
import kz.afm.candidate.test.variant.VariantEntity;
import kz.afm.candidate.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TestSessionService {

    private final TestSessionRepository testSessionRepository;
    private final TestSessionStatusService testSessionStatusService;
    private final TestSessionAnswerService testSessionAnswerService;
    private final CandidateService candidateService;
    private final AssessmentService assessmentService;
    private final ResultService resultService;

    public long createFromUserAndVariant(UserEntity user, VariantEntity variant) {
        final long requestingUserId = user.getId();
        final CandidateEntity candidate = this.candidateService.getByUserId(requestingUserId);
        return this.createFromCandidateAndVariant(candidate, variant).getId();
    }

    public TestSessionEntity createFromCandidateAndVariant(CandidateEntity candidate, VariantEntity variant) {
        final TestSessionStatusEntity startStatus = this.testSessionStatusService.getStartStatus();
        final TestSessionEntity newTestSession = new TestSessionEntity(variant, candidate, startStatus);
        return this.testSessionRepository.save(newTestSession);
    }

    public TestSessionEntity end(long testSessionId, List<TestSessionAnswerRequest> answers) throws NoSuchElementException {
        final TestSessionEntity testSession = this.getById(testSessionId);
        this.testSessionAnswerService.save(testSession, answers);
        return this.end(testSession);
    }

    private TestSessionEntity end(TestSessionEntity testSession) {
        final TestSessionStatusEntity endStatus = this.testSessionStatusService.getEndStatus();
        testSession.setStatus(endStatus);
        testSession.setEndDate(new Date());
        return this.testSessionRepository.save(testSession);
    }

    @Transactional
    public void deleteByCandidateIdentificationNumber(String candidateIdentificationNumber) {
        List<TestSessionEntity> testSessions = this.testSessionRepository.findAllByCandidate_IdentificationNumber(
                candidateIdentificationNumber
        );
        testSessions.forEach((TestSessionEntity testSession) -> {
            this.assessmentService.deleteByTestSession(testSession);
            this.resultService.deleteByTestSession(testSession);
            this.testSessionAnswerService.deleteByTestSession(testSession);
            this.testSessionRepository.delete(testSession);
        });
    }

    public void evaluate(TestSessionEntity testSession, List<ConditionalSectioningVariableValueDto> varValues) {
        if (!testSession.getVariant().getTest().getType().isAutomaticallyEvaluated()) return;

        this.resultService.evaluate(testSession, varValues);
        final TestSessionStatusEntity checkedStatus = this.testSessionStatusService.getCheckedStatus();
        testSession.setStatus(checkedStatus);
        this.testSessionRepository.save(testSession);
    }

    public void assess(long testSessionId, List<TestSessionAnswerDto> answers) throws NoSuchElementException {
        final TestSessionEntity testSession = this.getById(testSessionId);
        this.assessmentService.save(testSession, answers);
        final TestSessionStatusEntity checkedStatus = this.testSessionStatusService.getCheckedStatus();
        testSession.setStatus(checkedStatus);
        this.testSessionRepository.save(testSession);
    }

    public TestSessionEntity getById(long testSessionId) throws NoSuchElementException {
        return this.testSessionRepository.findById(testSessionId).orElseThrow(
                () -> new NoSuchElementException("Нет сессии теста с ID: " + testSessionId)
        );
    }

    public long countAllByRegionId(int regionId) {
        TestSessionStatusEntity status = this.testSessionStatusService.getEndStatus();

        if (regionId != -1) {
            return this.testSessionRepository.countAllByStatusAndCandidate_TestingRegion_Id(status, regionId);
        } else {
            return this.testSessionRepository.countAllByStatus(status);
        }
    }

    public List<TestSessionEntity> getAllByPurpose(
            String purpose,
            int regionId,
            int pageNumber,
            int pageSize
    ) throws IllegalArgumentException {
        if (Objects.equals(purpose, "assessment")) {
            return this.getAllForAssessment(regionId, pageNumber, pageSize);
        } else if (Objects.equals(purpose, "result")) {
            return this.getAllForResults(regionId, pageNumber, pageSize);
        } else {
            throw new IllegalArgumentException();
        }
    }

    private List<TestSessionEntity> getAllForAssessment(int regionId, int pageNumber, int pageSize) {
        TestSessionStatusEntity status = this.testSessionStatusService.getEndStatus();
        return this.getAllByStatusAndRegionId(status, regionId, pageNumber, pageSize);
    }

    private List<TestSessionEntity> getAllForResults(int regionId, int pageNumber, int pageSize) {
        TestSessionStatusEntity status = this.testSessionStatusService.getCheckedStatus();
        return this.getAllByStatusAndRegionId(status, regionId, pageNumber, pageSize);
    }

    private List<TestSessionEntity> getAllByStatusAndRegionId(
            TestSessionStatusEntity status,
            int regionId,
            int pageNumber,
            int pageSize
    ) {
        if (pageSize == -1) {
            return this.getAllByStatusAndRegionIdWithNoPagination(status, regionId);
        }

        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        if (regionId != -1) {
            return this.testSessionRepository.findAllByStatusAndCandidate_TestingRegion_Id(status, regionId, pageRequest);
        } else {
            return this.testSessionRepository.findAllByStatus(status, pageRequest);
        }
    }

    private List<TestSessionEntity> getAllByStatusAndRegionIdWithNoPagination(
            TestSessionStatusEntity status,
            int regionId
    ) {
        if (regionId != -1) {
            return this.testSessionRepository.findAllByStatusAndCandidate_TestingRegion_Id(status, regionId);
        } else {
            return this.testSessionRepository.findAllByStatus(status);
        }
    }

    public List<TestSessionEntity> getAllByCandidate(CandidateEntity candidate) {
        return this.testSessionRepository.findAllByCandidate(candidate);
    }

}
