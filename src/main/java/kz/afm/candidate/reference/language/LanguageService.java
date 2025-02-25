package kz.afm.candidate.reference.language;

import kz.afm.candidate.candidate.dto.CandidateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class LanguageService {

    private final LanguageRepository languageRepository;

    public Set<LanguageEntity> getSetOfAllUsing(CandidateRequest candidateDto) {
        return this.getSetOfAllByCodes(candidateDto.languageCodes, true);
    }

    public Set<LanguageEntity> getSetOfAllByCodes(Set<String> codes, boolean notEmpty) throws NoSuchElementException {
        if (codes.isEmpty()) return new HashSet<>();
        final Set<LanguageEntity> languages = new HashSet<>(
                this.languageRepository.findAllById(codes)
        );
        if (!notEmpty) return languages;

        if (languages.isEmpty()) throw new NoSuchElementException("Языки не найдены");
        return languages;
    }

    public List<LanguageEntity> getAll(boolean notEmpty) throws NoSuchElementException {
        final List<LanguageEntity> languages = this.languageRepository.findAll();
        if (!notEmpty) return languages;

        if (languages.isEmpty()) throw new NoSuchElementException("Языки не найдены");
        return languages;
    }

}
