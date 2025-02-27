package kz.afm.candidate.reference.language.level;

import kz.afm.candidate.dto.ResponseBodyWrapper;
import kz.afm.candidate.reference.language.level.dto.LanguageLevelResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("language/level")
@RestController
public class LanguageLevelController {

    private final LanguageLevelService languageLevelService;

    @GetMapping("all")
    public ResponseEntity<ResponseBodyWrapper<List<LanguageLevelResponse>>> getAll() {
        try {
            final List<LanguageLevelEntity> levels = this.languageLevelService.getAll();
            final List<LanguageLevelResponse> levelDtoList = levels.stream().map(LanguageLevelResponse::new).toList();
            return ResponseEntity.ok(ResponseBodyWrapper.success(levelDtoList));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        }
    }

}
