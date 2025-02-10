package kz.afm.candidate.test.test_type.point_distribution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointDistributionTestRepository extends JpaRepository<PointDistributionTestEntity, Long> {
}
