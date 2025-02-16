package kz.afm.candidate.test.session;

import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.test.session.status.TestSessionStatusEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestSessionRepository extends JpaRepository<TestSessionEntity, Long> {
    List<TestSessionEntity> findAllByStatus(TestSessionStatusEntity status);

    List<TestSessionEntity> findAllByStatus(TestSessionStatusEntity status, Pageable pageable);

    List<TestSessionEntity> findAllByStatusAndCandidate_TestingRegion_Id(TestSessionStatusEntity status, int candidateTestingRegionId);

    List<TestSessionEntity> findAllByStatusAndCandidate_TestingRegion_Id(TestSessionStatusEntity status, int candidateTestingRegionId, Pageable pageable);

    long countAllByStatus(TestSessionStatusEntity status);

    long countAllByStatusAndCandidate_TestingRegion_Id(TestSessionStatusEntity status, int candidateTestingRegionId);

    List<TestSessionEntity> findAllByCandidate(CandidateEntity candidate);
}
