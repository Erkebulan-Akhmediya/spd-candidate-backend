package kz.afm.candidate.candidate.language_knowledge;

import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.candidate.dto.LanguageKnowledgeDto;
import kz.afm.candidate.reference.language.LanguageEntity;
import kz.afm.candidate.reference.language.LanguageService;
import kz.afm.candidate.reference.language.level.LanguageLevelEntity;
import kz.afm.candidate.reference.language.level.LanguageLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class LanguageKnowledgeService {

    private final LanguageService languageService;
    private final LanguageLevelService languageLevelService;

    private final LanguageKnowledgeRepository languageKnowledgeRepository;

    public List<LanguageKnowledgeEntity> getAllByCandidate(CandidateEntity candidate) {
        return this.languageKnowledgeRepository.findAllByCandidate(candidate);
    }

    public void updateAll(CandidateEntity candidate, List<LanguageKnowledgeDto> languageKnowledgeDtoList) {
        this.languageKnowledgeRepository.deleteAllByCandidate(candidate);
        this.createAll(candidate, languageKnowledgeDtoList);
    }

    public void createAll(CandidateEntity candidate, List<LanguageKnowledgeDto> languageKnowledgeDtoList) {
        final List<String> languageCodes = new LinkedList<>();
        final List<String> levelCodes = new LinkedList<>();
        languageKnowledgeDtoList.forEach((LanguageKnowledgeDto knowledgeDto) -> {
            languageCodes.add(knowledgeDto.languageCode);
            levelCodes.add(knowledgeDto.levelCode);
        });

        final Map<String, LanguageEntity> codeToLanguage = this.languageService.getMapOfAllByCodes(languageCodes);
        final Map<String, LanguageLevelEntity> codeToLevel = this.languageLevelService.getMapOfAllByCodes(levelCodes);

        final List<LanguageKnowledgeEntity> knowledge = languageKnowledgeDtoList.stream()
                .map(
                        (LanguageKnowledgeDto knowledgeDto) -> {
                            final LanguageEntity language = codeToLanguage.get(knowledgeDto.languageCode);
                            final LanguageLevelEntity level = codeToLevel.get(knowledgeDto.levelCode);
                            return new LanguageKnowledgeEntity(candidate, language, level);
                        }
                )
                .toList();
        this.languageKnowledgeRepository.saveAll(knowledge);
    }

}
