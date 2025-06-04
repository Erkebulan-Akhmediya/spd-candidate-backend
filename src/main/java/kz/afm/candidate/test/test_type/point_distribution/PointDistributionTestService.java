package kz.afm.candidate.test.test_type.point_distribution;

import kz.afm.candidate.test.TestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class PointDistributionTestService {

    public static final int TYPE_ID = 5;

    private final PointDistributionTestRepository pointDistributionTestRepository;

    public void create(TestEntity test, int maxPointsPerQuestion) {
        final PointDistributionTestEntity pointDistributionTest = new PointDistributionTestEntity(test, maxPointsPerQuestion);
        this.pointDistributionTestRepository.save(pointDistributionTest);
    }

    public int getMaxPointsPerQuestionByTest(TestEntity test) {
        if (test.getType().getId() != TYPE_ID) return 0;
        return this.getByTestId(test.getId()).getMaxPointsPerQuestion();
    }

    private PointDistributionTestEntity getByTestId(long testId) throws NoSuchElementException {
        return this.pointDistributionTestRepository.findByTest_Id(testId).orElseThrow(
                () -> new NoSuchElementException("Нет данных для теста по распределению баллов с TestID: " + testId)
        );
    }

}
