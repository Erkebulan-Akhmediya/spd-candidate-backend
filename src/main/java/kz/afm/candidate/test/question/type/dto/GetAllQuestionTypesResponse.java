package kz.afm.candidate.test.question.type.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetAllQuestionTypesResponse {
    private String error;
    private List<QuestionTypeDto> questionTypes;
}
