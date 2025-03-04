package kz.afm.candidate.test.session.evaluation.result;

import kz.afm.candidate.test.session.TestSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<ResultEntity, Long> {
    List<ResultEntity> findAllByTestSession(TestSessionEntity testSession);

    void deleteByTestSession(TestSessionEntity testSession);
}
