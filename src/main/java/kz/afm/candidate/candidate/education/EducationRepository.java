package kz.afm.candidate.candidate.education;

import kz.afm.candidate.candidate.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<EducationEntity, Long> {
    void deleteAllByCandidate(CandidateEntity candidate);
    List<EducationEntity> findAllByCandidate(CandidateEntity candidate);
}
