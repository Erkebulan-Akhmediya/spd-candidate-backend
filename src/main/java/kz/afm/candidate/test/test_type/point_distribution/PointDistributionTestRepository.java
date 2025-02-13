package kz.afm.candidate.test.test_type.point_distribution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PointDistributionTestRepository extends JpaRepository<PointDistributionTestEntity, Long> {
    Optional<PointDistributionTestEntity> findByTest_Id(long testId);
}
