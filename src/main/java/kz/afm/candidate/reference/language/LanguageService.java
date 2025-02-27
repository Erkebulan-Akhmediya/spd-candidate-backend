package kz.afm.candidate.reference.language;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public List<LanguageEntity> getAll(boolean notEmpty) throws NoSuchElementException {
        final List<LanguageEntity> languages = this.languageRepository.findAll();
        if (!notEmpty) return languages;

        if (languages.isEmpty()) throw new NoSuchElementException("Языки не найдены");
        return languages;
    }

    public Map<String, LanguageEntity> getMapOfAllByCodes(List<String> codes) throws NoSuchElementException {
        final List<LanguageEntity> languages = this.languageRepository.findAllByCodeIn(codes);
        final Map<String, LanguageEntity> codeToLanguage = new HashMap<>();
        for (LanguageEntity languageEntity : languages) {
            codeToLanguage.put(languageEntity.getCode(), languageEntity);
        }
        return codeToLanguage;
    }

}
