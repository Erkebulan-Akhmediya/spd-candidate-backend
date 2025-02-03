package kz.afm.candidate.reference.nationality;

import kz.afm.candidate.dto.ResponseBodyWrapper;
import kz.afm.candidate.reference.nationality.dto.NationalityResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RequestMapping("nationality")
@RestController
public class NationalityController {

    private final NationalityService nationalityService;

    @GetMapping("all")
    public ResponseEntity<ResponseBodyWrapper<List<NationalityResponse>>> getAll() {
        try {
            final List<NationalityResponse> nationalities = this.nationalityService.getAll(true)
                    .stream()
                    .map(NationalityResponse::fromEntity)
                    .toList();
            return ResponseEntity.ok().body(ResponseBodyWrapper.success(nationalities));
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(
                    ResponseBodyWrapper.error(e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    ResponseBodyWrapper.error("Ошибка сервера")
            );
        }
    }

}
