package kz.afm.candidate.test.session.evaluation.section.conditional.variable;

import kz.afm.candidate.test.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConditionalSectioningVariableRepository extends JpaRepository<ConditionalSectioningVariableEntity, Long> {
    List<ConditionalSectioningVariableEntity> findAllByTest(TestEntity test);
}
