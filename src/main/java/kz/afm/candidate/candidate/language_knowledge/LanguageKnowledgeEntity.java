package kz.afm.candidate.candidate.language_knowledge;

import jakarta.persistence.*;
import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.reference.language.LanguageEntity;
import kz.afm.candidate.reference.language.level.LanguageLevelEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "language_knowledge")
public class LanguageKnowledgeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @ManyToOne
    @JoinColumn(name = "candidate_identification_number", nullable = false)
    public CandidateEntity candidate;

    @ManyToOne
    @JoinColumn(name = "language_code", nullable = false)
    public LanguageEntity language;

    @ManyToOne
    @JoinColumn(name = "level_code", nullable = false)
    public LanguageLevelEntity level;

    public LanguageKnowledgeEntity(CandidateEntity candidate, LanguageEntity language, LanguageLevelEntity level) {
        this.candidate = candidate;
        this.language = language;
        this.level = level;
    }

}
