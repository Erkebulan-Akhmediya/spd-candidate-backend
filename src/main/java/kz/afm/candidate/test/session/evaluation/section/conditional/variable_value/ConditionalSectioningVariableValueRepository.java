package kz.afm.candidate.test.session.evaluation.section.conditional.variable_value;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConditionalSectioningVariableValueRepository extends JpaRepository<ConditionalSectioningVariableValueEntity, Long> {
}
