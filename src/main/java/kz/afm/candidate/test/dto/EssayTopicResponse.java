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
        this.variantId = question.getVariant().getId();
        this.nameRus = question.getNameRus();
        this.nameKaz = question.getNameKaz();
    }

}
