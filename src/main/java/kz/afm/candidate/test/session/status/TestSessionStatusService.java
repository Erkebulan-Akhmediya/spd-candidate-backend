package kz.afm.candidate.test.session.status;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class TestSessionStatusService {

    private final TestSessionStatusRepository testSessionStatusRepository;

    public TestSessionStatusEntity getStartStatus() {
        final int startedTestSessionStatusId = 1;
        return this.getById(startedTestSessionStatusId);
    }

    public TestSessionStatusEntity getEndStatus() {
        final int endedTestSessionStatusId = 2;
        return this.getById(endedTestSessionStatusId);
    }

    public TestSessionStatusEntity getById(int id) {
        return testSessionStatusRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Статус сессии теста с ID " + id + " не найден")
        );
    }

}
