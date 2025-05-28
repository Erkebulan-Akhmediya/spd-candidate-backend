package kz.afm.candidate.test.session.dto;

import kz.afm.candidate.test.question.QuestionEntity;
import kz.afm.candidate.test.session.answer.TestSessionAnswerEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TestSessionAnswerDto {
    public long id;
    public String questionNameRus;
    public String questionNameKaz;
    public String answer;
    public String assessment;
    public String fileUrl;

    public TestSessionAnswerDto(TestSessionAnswerEntity answerEntity) {
        this.id = answerEntity.getId();

        QuestionEntity question = answerEntity.getQuestion();
        this.questionNameRus = question.nameRus;
        this.questionNameKaz = question.nameKaz;

        this.answer = answerEntity.getAnswer();
        this.assessment = "";
    }
}
