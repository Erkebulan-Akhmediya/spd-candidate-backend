package kz.afm.candidate.test.evaluation.result;

import kz.afm.candidate.dto.ResponseBodyWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("test/session/result")
@RestController
public class ResultController {

    private final ResultService resultService;

    @GetMapping("{test_session_id}")
    public ResponseEntity<ResponseBodyWrapper<Void>> get(@PathVariable(name = "test_session_id") Long testSessionId) {
        try {
            this.resultService.get(testSessionId);
            return ResponseEntity.ok(ResponseBodyWrapper.success());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

}
