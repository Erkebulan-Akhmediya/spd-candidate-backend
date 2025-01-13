package kz.afm.candidate.reference.language;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public Set<LanguageEntity> getAllSetByCodes(Set<String> codes) {
        return new HashSet<>(this.languageRepository.findAllById(codes));
    }

    public Set<LanguageEntity> getAllSetByCodes(Set<String> codes, boolean notEmpty) throws NoSuchElementException {
        final Set<LanguageEntity> languages = new HashSet<>(
                this.languageRepository.findAllById(codes)
        );
        if (!notEmpty) return languages;

        if (languages.isEmpty()) throw new NoSuchElementException("Языки не найдены");
        return languages;
    }

}
