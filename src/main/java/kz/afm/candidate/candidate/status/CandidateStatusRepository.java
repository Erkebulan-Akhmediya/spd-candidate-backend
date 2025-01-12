package kz.afm.candidate.candidate.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateStatusRepository extends JpaRepository<CandidateStatusEntity, Integer> {
}
