package kz.afm.candidate.candidate.language_knowledge;

import kz.afm.candidate.candidate.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LanguageKnowledgeRepository extends JpaRepository<LanguageKnowledgeEntity, Long> {
    void deleteByCandidate(CandidateEntity candidate);

    void deleteByCandidate_IdentificationNumber(String candidateIdentificationNumber);

    List<LanguageKnowledgeEntity> findAllByCandidate(CandidateEntity candidate);
}
