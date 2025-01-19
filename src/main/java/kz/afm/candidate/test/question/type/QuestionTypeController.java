package kz.afm.candidate.test.question.type;

import kz.afm.candidate.test.question.type.dto.GetAllQuestionTypesResponse;
import kz.afm.candidate.test.question.type.dto.QuestionTypeDto;
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
    public ResponseEntity<GetAllQuestionTypesResponse> getAll() {
        try {
            final List<QuestionTypeDto> types = this.questionTypeService.getAll()
                    .stream()
                    .map(
                            (QuestionTypeEntity type) -> new QuestionTypeDto(
                                    type.getId(),
                                    type.getNameRus(),
                                    type.getNameKaz()
                            )
                    )
                    .toList();
            return ResponseEntity.ok(new GetAllQuestionTypesResponse(null, types));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new GetAllQuestionTypesResponse("Ошибка на сервере", null)
            );
        }
    }

}
