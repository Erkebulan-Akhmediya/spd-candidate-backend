package kz.afm.candidate.test.session;

import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.candidate.CandidateService;
import kz.afm.candidate.test.session.status.TestSessionStatusEntity;
import kz.afm.candidate.test.session.status.TestSessionStatusService;
import kz.afm.candidate.test.variant.VariantEntity;
import kz.afm.candidate.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class TestSessionService {

    private final TestSessionRepository testSessionRepository;
    private final TestSessionStatusService testSessionStatusService;
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

    public void end(long testSessionId) throws NoSuchElementException {
        final TestSessionEntity testSession = this.getById(testSessionId);
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

}
