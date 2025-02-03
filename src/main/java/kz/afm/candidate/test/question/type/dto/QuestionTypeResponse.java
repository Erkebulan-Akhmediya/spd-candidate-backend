package kz.afm.candidate.test.question.type.dto;

import kz.afm.candidate.test.question.type.QuestionTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QuestionTypeResponse {
    private int id;
    private String nameRus;
    private String nameKaz;

    public static QuestionTypeResponse fromEntity(QuestionTypeEntity type) {
        return new QuestionTypeResponse(type.getId(), type.getNameRus(), type.getNameKaz());
    }

    public static List<QuestionTypeResponse> fromEntities(List<QuestionTypeEntity> types) {
        return types.stream().map(QuestionTypeResponse::fromEntity).toList();
    }

}
