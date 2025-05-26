package kz.afm.candidate.test.question.dto;

import kz.afm.candidate.test.question.QuestionEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseBody {
    public long id;
    public String nameRus;
    public String nameKaz;
    public boolean withFile;
    public String fileUrl;
    public boolean isDisappearing;
    public int timeToDisappear;
    public List<OptionResponseBody> options;

    public QuestionResponseBody(QuestionEntity question, List<OptionResponseBody> options, String fileUrl) {
        this.id = question.id;
        this.nameRus = question.nameRus;
        this.nameKaz = question.nameKaz;
        this.withFile = question.withFile;
        this.fileUrl = fileUrl;
        this.isDisappearing = question.isDisappearing;
        this.timeToDisappear = question.timeToDisappear;
        this.options = options;
    }
}
