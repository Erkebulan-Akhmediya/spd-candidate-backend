package kz.afm.candidate.test.session;

import kz.afm.candidate.candidate.CandidateService;
import kz.afm.candidate.test.TestService;
import kz.afm.candidate.test.session.status.TestSessionStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TestSessionService {

    private final TestSessionRepository testSessionRepository;
    private final TestSessionStatusService testSessionStatusService;
    private final CandidateService candidateService;
    private final TestService testService;

    public TestSessionEntity create(String candidateIin, long testId) {
        return this.testSessionRepository.save(
                new TestSessionEntity(
                        this.testService.getById(testId),
                        this.candidateService.getById(candidateIin),
                        this.testSessionStatusService.getById(1)
                )
        );
    }

}
