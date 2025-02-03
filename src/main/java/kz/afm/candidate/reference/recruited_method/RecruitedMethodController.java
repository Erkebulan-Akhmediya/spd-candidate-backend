package kz.afm.candidate.reference.recruited_method;

import kz.afm.candidate.dto.ResponseBodyWrapper;
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
    public ResponseEntity<ResponseBodyWrapper<List<RecruitedMethodResponse>>> getAll() {
        try {
            final List<RecruitedMethodResponse> recruitedMethods = this.recruitedMethodService.getAll(true)
                    .stream()
                    .map(RecruitedMethodResponse::fromEntity)
                    .toList();
            return ResponseEntity.ok(ResponseBodyWrapper.success(recruitedMethods));
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
