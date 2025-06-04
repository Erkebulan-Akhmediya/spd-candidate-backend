package kz.afm.candidate.test.dto;

import kz.afm.candidate.test.question.QuestionEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {
    public boolean withFile;
    public String fileName;
    public String nameRus;
    public String nameKaz;
    public boolean isDisappearing;
    public int timeToDisappear;
    public List<OptionDto> options;

    public QuestionDto(QuestionEntity question, List<OptionDto> options) {
        this.withFile = question.withFile;
        this.fileName = question.fileName;
        this.nameRus = question.nameRus;
        this.nameKaz = question.nameKaz;
        this.isDisappearing = question.isDisappearing;
        this.timeToDisappear = question.timeToDisappear;
        this.options = options;
    }

}
