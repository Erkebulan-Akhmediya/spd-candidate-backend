package kz.afm.candidate.test.session.evaluation.increment;

import kz.afm.candidate.test.option.OptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OptionIncrementRepository extends JpaRepository<OptionIncrementEntity, Long> {
    Optional<OptionIncrementEntity> findByOption(OptionEntity option);
}
