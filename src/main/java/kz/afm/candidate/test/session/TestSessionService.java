package kz.afm.candidate.test.session;

import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.candidate.CandidateService;
import kz.afm.candidate.test.session.status.TestSessionStatusService;
import kz.afm.candidate.test.variant.VariantEntity;
import kz.afm.candidate.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestSessionService {

    private final TestSessionRepository testSessionRepository;
    private final TestSessionStatusService testSessionStatusService;
    private final CandidateService candidateService;

    public TestSessionEntity create(CandidateEntity candidate, VariantEntity variant) {
        return this.testSessionRepository.save(
                new TestSessionEntity(
                        variant,
                        candidate,
                        this.testSessionStatusService.getById(1)
                )
        );
    }

    public long create(UserEntity user, VariantEntity variant) {
        final long requestingUserId = user.getId();

        final CandidateEntity candidate = this.candidateService.getByUserId(requestingUserId);

        return this.create(candidate, variant).getId();
    }

}
