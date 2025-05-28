package kz.afm.candidate.test.dto;

import kz.afm.candidate.test.question.QuestionEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class EssayTopicResponse {
    public long variantId;
    public String nameRus;
    public String nameKaz;

    public EssayTopicResponse(QuestionEntity question) {
        this.variantId = question.variant.getId();
        this.nameRus = question.nameRus;
        this.nameKaz = question.nameKaz;
    }

}
