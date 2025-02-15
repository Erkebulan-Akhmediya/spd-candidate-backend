package kz.afm.candidate.test.session.dto;

import kz.afm.candidate.test.question.QuestionEntity;
import kz.afm.candidate.test.session.answer.TestSessionAnswerEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TestSessionAnswerForAssessment {
    public long id;
    public String questionNameRus;
    public String questionNameKaz;
    public String answer;
    public String assessment;

    public TestSessionAnswerForAssessment(TestSessionAnswerEntity answer) {
        this.id = answer.getId();

        QuestionEntity question = answer.getQuestion();
        this.questionNameRus = question.getNameRus();
        this.questionNameKaz = question.getNameKaz();

        this.answer = answer.getAnswer();
        this.assessment = "";
    }
}
