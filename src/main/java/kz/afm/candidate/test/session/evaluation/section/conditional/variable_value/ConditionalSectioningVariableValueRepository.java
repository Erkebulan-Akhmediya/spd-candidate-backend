package kz.afm.candidate.test.session.evaluation.section.conditional.variable_value;

import kz.afm.candidate.test.session.TestSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConditionalSectioningVariableValueRepository extends JpaRepository<ConditionalSectioningVariableValueEntity, Long> {
    List<ConditionalSectioningVariableValueEntity> findAllByTestSession(TestSessionEntity testSession);
}
