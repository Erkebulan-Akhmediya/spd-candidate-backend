package kz.afm.candidate.reference.language;

import kz.afm.candidate.dto.ResponseBodyWrapper;
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
    public ResponseEntity<ResponseBodyWrapper<List<LanguageResponse>>> getAll() {
        try {
            final List<LanguageResponse> languages = this.languageService.getAll(true)
                    .stream()
                    .map(LanguageResponse::new)
                    .toList();
            return ResponseEntity.ok(ResponseBodyWrapper.success(languages));
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(
                    ResponseBodyWrapper.error(e.getMessage())
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(
                    ResponseBodyWrapper.error("Ошибка на сервере")
            );
        }
    }

}
