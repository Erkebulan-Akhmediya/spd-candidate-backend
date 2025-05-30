package kz.afm.candidate.candidate.experience;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<ExperienceEntity, Long> {
    List<ExperienceEntity> findByCandidate_IdentificationNumber(String candidateIdentificationNumber);

    void deleteByCandidate_IdentificationNumber(String candidateIdentificationNumber);
}
