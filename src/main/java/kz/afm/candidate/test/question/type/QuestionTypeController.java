package kz.afm.candidate.test.question.type;

import kz.afm.candidate.dto.ResponseBodyWrapper;
import kz.afm.candidate.test.question.type.dto.QuestionTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("question/type")
@RestController
public class QuestionTypeController {

    private final QuestionTypeService questionTypeService;

    @GetMapping("all")
    public ResponseEntity<ResponseBodyWrapper<List<QuestionTypeResponse>>> getAll() {
        try {
            final List<QuestionTypeResponse> types = QuestionTypeResponse.fromEntities(this.questionTypeService.getAll());
            return ResponseEntity.ok(ResponseBodyWrapper.success(types));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    ResponseBodyWrapper.error("Ошибка на сервере")
            );
        }
    }

}
