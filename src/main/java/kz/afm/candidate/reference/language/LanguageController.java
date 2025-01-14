package kz.afm.candidate.reference.language;

import kz.afm.candidate.reference.language.dto.GetAllLanguagesResponse;
import kz.afm.candidate.reference.language.dto.LanguageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RequestMapping("language")
@RestController
public class LanguageController {

    private final LanguageService languageService;

    @GetMapping("all")
    public ResponseEntity<GetAllLanguagesResponse> getAll() {
        try {
            final List<LanguageResponse> languages = this.languageService.getAll(true)
                    .stream()
                    .map(
                            (LanguageEntity language) -> new LanguageResponse(
                                    language.getCode(),
                                    language.getNameRus(),
                                    language.getNameKaz()
                            )
                    )
                    .toList();
            return ResponseEntity.ok().body(new GetAllLanguagesResponse(null, languages));
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(
                    new GetAllLanguagesResponse(e.toString(), null)
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new GetAllLanguagesResponse("Ошибка на сервере", null)
            );
        }
    }

}
