package kz.afm.candidate.reference.nationality;

import kz.afm.candidate.reference.nationality.dto.GetAllNationalityResponse;
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
    public ResponseEntity<GetAllNationalityResponse> getAll() {
        try {
            final List<NationalityResponse> nationalities = this.nationalityService.getAll(true)
                    .stream()
                    .map(
                            (NationalityEntity nationality) -> new NationalityResponse(
                                    nationality.getCode(),
                                    nationality.getNameKaz(),
                                    nationality.getNameRus()
                            )
                    )
                    .toList();
            return ResponseEntity.ok().body(new GetAllNationalityResponse(null, nationalities));
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(
                    new GetAllNationalityResponse(e.toString(), null)
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new GetAllNationalityResponse("Ошибка сервера", null)
            );
        }
    }

}
