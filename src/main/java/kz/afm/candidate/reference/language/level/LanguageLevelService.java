package kz.afm.candidate.reference.language.level;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class LanguageLevelService {

    private final LanguageLevelRepository languageLevelRepository;

    public List<LanguageLevelEntity> getAll() {
        final List<LanguageLevelEntity> levels = this.languageLevelRepository.findAll();
        if (levels.isEmpty()) {
            throw new NoSuchElementException("Ошибка получения справочных данных по уровням владения языков");
        }
        return levels;
    }

    public Map<String, LanguageLevelEntity> getMapOfAllByCodes(List<String> codes) {
        final List<LanguageLevelEntity> levels = this.languageLevelRepository.findAllByCodeIn(codes);
        final Map<String, LanguageLevelEntity> codeToLevel = new HashMap<>();
        for (LanguageLevelEntity level : levels) {
            codeToLevel.put(level.code, level);
        }
        return codeToLevel;
    }

}
