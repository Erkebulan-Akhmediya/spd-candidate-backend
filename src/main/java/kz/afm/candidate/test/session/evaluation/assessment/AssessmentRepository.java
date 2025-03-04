package kz.afm.candidate.test.session.evaluation.assessment;

import kz.afm.candidate.test.session.TestSessionEntity;
import kz.afm.candidate.test.session.answer.TestSessionAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends JpaRepository<AssessmentEntity, Long> {
    List<AssessmentEntity> findAllByTestSessionAnswer_TestSession(TestSessionEntity testSessionAnswerTestSession);

    void deleteByTestSessionAnswer(TestSessionAnswerEntity testSessionAnswer);
}
