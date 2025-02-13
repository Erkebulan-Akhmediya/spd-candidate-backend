package kz.afm.candidate.test.session.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestSessionAnswerRepository extends JpaRepository<TestSessionAnswerEntity, Long> {
}
