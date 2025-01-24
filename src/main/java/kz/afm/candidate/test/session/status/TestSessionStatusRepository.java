package kz.afm.candidate.test.session.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestSessionStatusRepository extends JpaRepository<TestSessionStatusEntity, Integer> {
}
