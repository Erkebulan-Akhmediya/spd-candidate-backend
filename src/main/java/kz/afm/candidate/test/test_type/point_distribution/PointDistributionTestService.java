package kz.afm.candidate.test.test_type.point_distribution;

import kz.afm.candidate.test.TestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class PointDistributionTestService {

    private final PointDistributionTestRepository pointDistributionTestRepository;

    public void create(TestEntity test, int maxPointsPerQuestion) {
        final PointDistributionTestEntity pointDistributionTest = new PointDistributionTestEntity(test, maxPointsPerQuestion);
        this.pointDistributionTestRepository.save(pointDistributionTest);
    }

    public int getMaxPointsPerQuestionByTestId(long testId) throws NoSuchElementException {
        return this.getPointDistributionTestById(testId).getMaxPointsPerQuestion();
    }

    private PointDistributionTestEntity getPointDistributionTestById(long testId) throws NoSuchElementException {
        return this.pointDistributionTestRepository.findById(testId).orElseThrow(
                () -> new NoSuchElementException("Нет данных для теста по распределению баллов с TestID: " + testId)
        );
    }

}
