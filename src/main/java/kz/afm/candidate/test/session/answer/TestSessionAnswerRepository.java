package kz.afm.candidate.test.session.answer;

import kz.afm.candidate.test.session.TestSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestSessionAnswerRepository extends JpaRepository<TestSessionAnswerEntity, Long> {
    List<TestSessionAnswerEntity> findAllByTestSession(TestSessionEntity testSession);

    void deleteByTestSession(TestSessionEntity testSession);
}
