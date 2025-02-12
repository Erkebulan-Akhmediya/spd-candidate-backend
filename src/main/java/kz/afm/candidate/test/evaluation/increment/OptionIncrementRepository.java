package kz.afm.candidate.test.evaluation.increment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OptionIncrementRepository extends JpaRepository<OptionIncrementEntity, Long> {
}
