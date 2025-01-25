package kz.afm.candidate.test.option;

import kz.afm.candidate.test.question.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionRepository extends JpaRepository<OptionEntity, Long> {
    List<OptionEntity> findAllByQuestion(QuestionEntity question);
}
