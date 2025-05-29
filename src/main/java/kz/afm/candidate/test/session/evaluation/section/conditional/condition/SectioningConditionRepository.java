package kz.afm.candidate.test.session.evaluation.section.conditional.condition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectioningConditionRepository extends JpaRepository<SectioningConditionEntity, Long> {
}
