package kz.afm.candidate.test.session.evaluation.section.conditional.condition;

import kz.afm.candidate.test.session.evaluation.section.SectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SectioningConditionRepository extends JpaRepository<SectioningConditionEntity, Long> {
    List<SectioningConditionEntity> findAllBySection(SectionEntity section);
}
