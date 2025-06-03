package kz.afm.candidate.test.session.evaluation.section;

import kz.afm.candidate.test.session.evaluation.scale.ScaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectionRepository extends JpaRepository<SectionEntity, Long> {
    List<SectionEntity> findAllByScaleAndLowerBoundLessThanEqualAndUpperBoundGreaterThanEqual(
            ScaleEntity scale,
            int lowerBoundIsLessThan,
            int upperBoundIsGreaterThan
    );
}
