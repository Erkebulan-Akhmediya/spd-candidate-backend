package kz.afm.candidate.reference.recruited_method;

import kz.afm.candidate.reference.recruited_method.dto.GetAllRecruitedMethodResponse;
import kz.afm.candidate.reference.recruited_method.dto.RecruitedMethodResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RequestMapping("recruited_method")
@RestController
public class RecruitedMethodController {

    private final RecruitedMethodService recruitedMethodService;

    @GetMapping("all")
    public ResponseEntity<GetAllRecruitedMethodResponse> getAll() {
        try {
            final List<RecruitedMethodResponse> recruitedMethods = this.recruitedMethodService.getAll(true)
                    .stream()
                    .map(
                            (RecruitedMethodEntity method) -> new RecruitedMethodResponse(
                                    method.getId(),
                                    method.getNameRus(),
                                    method.getNameKaz()
                            )
                    )
                    .toList();
            return ResponseEntity.ok(new GetAllRecruitedMethodResponse(null, recruitedMethods));
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(
                    new GetAllRecruitedMethodResponse(e.getMessage(), null)
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new GetAllRecruitedMethodResponse("Ошибка сервера", null)
            );
        }
    }

}
