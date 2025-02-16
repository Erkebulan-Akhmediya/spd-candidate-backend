package kz.afm.candidate.test.session.status;

import kz.afm.candidate.test.TestEntity;
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

    public TestSessionStatusEntity getCheckedStatus() {
        final int checkedTestSessionStatusId = 3;
        return this.getById(checkedTestSessionStatusId);
    }

    public boolean isPointDistribution(TestEntity test) {
        final int pointDistributionTestSessionStatusId = 5;
        return test.getType().getId() == pointDistributionTestSessionStatusId;
    }

    public TestSessionStatusEntity getById(int id) {
        return testSessionStatusRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Статус сессии теста с ID " + id + " не найден")
        );
    }

}
