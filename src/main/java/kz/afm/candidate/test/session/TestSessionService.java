package kz.afm.candidate.test.session;

import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.candidate.CandidateService;
import kz.afm.candidate.test.session.answer.TestSessionAnswerService;
import kz.afm.candidate.test.session.dto.TestSessionAnswerRequest;
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

@RequiredArgsConstructor
@Service
public class TestSessionService {

    private final TestSessionRepository testSessionRepository;
    private final TestSessionStatusService testSessionStatusService;
    private final TestSessionAnswerService testSessionAnswerService;
    private final CandidateService candidateService;

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

    public void end(long testSessionId, List<TestSessionAnswerRequest> answers) throws NoSuchElementException {
        final TestSessionEntity testSession = this.getById(testSessionId);
        this.testSessionAnswerService.save(testSession, answers);
        this.end(testSession);
    }

    private void end(TestSessionEntity testSession) {
        final TestSessionStatusEntity endStatus = this.testSessionStatusService.getEndStatus();
        testSession.setStatus(endStatus);
        testSession.setEndDate(new Date());
        this.testSessionRepository.save(testSession);
    }

    public TestSessionEntity getById(long testSessionId) throws NoSuchElementException {
        return this.testSessionRepository.findById(testSessionId).orElseThrow(
                () -> new NoSuchElementException("Нет сессии теста с ID: " + testSessionId)
        );
    }

    public long countAllForAssessment(int regionId, boolean checked) {
        TestSessionStatusEntity status;
        if (checked) {
            status = this.testSessionStatusService.getCheckedStatus();
        } else {
            status = this.testSessionStatusService.getEndStatus();
        }

        if (regionId != -1) {
            return this.testSessionRepository.countAllByStatusAndCandidate_TestingRegion_Id(status, regionId);
        } else {
            return this.testSessionRepository.countAllByStatus(status);
        }
    }

    public List<TestSessionEntity> getAllForAssessment(int regionId, boolean checked, int pageNumber, int pageSize) {
        TestSessionStatusEntity status;
        if (checked) {
            status = this.testSessionStatusService.getCheckedStatus();
        } else {
            status = this.testSessionStatusService.getEndStatus();
        }


        if (pageSize == -1) {
            return this.getAllForAssessmentWithoutPagination(status, regionId);
        }

        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        if (regionId != -1) {
            return this.testSessionRepository.findAllByStatusAndCandidate_TestingRegion_Id(status, regionId, pageRequest);
        } else {
            return this.testSessionRepository.findAllByStatus(status, pageRequest);
        }
    }

    private List<TestSessionEntity> getAllForAssessmentWithoutPagination(TestSessionStatusEntity status, int regionId) {
        if (regionId != -1) {
            return this.testSessionRepository.findAllByStatusAndCandidate_TestingRegion_Id(status, regionId);
        } else {
            return this.testSessionRepository.findAllByStatus(status);
        }
    }

}
