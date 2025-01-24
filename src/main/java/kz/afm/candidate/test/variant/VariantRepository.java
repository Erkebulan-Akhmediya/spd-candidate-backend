package kz.afm.candidate.test.variant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VariantRepository extends JpaRepository<VariantEntity, Long> {
    List<VariantEntity> findAllByTest_Id(long testId);
}
