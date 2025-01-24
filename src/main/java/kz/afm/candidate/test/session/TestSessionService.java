package kz.afm.candidate.test.session;

import kz.afm.candidate.candidate.CandidateService;
import kz.afm.candidate.test.session.status.TestSessionStatusService;
import kz.afm.candidate.test.variant.VariantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestSessionService {

    private final TestSessionRepository testSessionRepository;
    private final TestSessionStatusService testSessionStatusService;
    private final CandidateService candidateService;

    public TestSessionEntity create(String candidateIin, VariantEntity variant) {
        return this.testSessionRepository.save(
                new TestSessionEntity(
                        variant,
                        this.candidateService.getById(candidateIin),
                        this.testSessionStatusService.getById(1)
                )
        );
    }

}
