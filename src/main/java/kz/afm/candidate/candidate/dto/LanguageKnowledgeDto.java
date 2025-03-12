package kz.afm.candidate.candidate.dto;

import kz.afm.candidate.candidate.language_knowledge.LanguageKnowledgeEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class LanguageKnowledgeDto {
    public String languageCode;
    public String levelCode;

    public LanguageKnowledgeDto(LanguageKnowledgeEntity knowledge) {
        this.languageCode = knowledge.language.code;
        this.levelCode = knowledge.level.code;
    }
}
