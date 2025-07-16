package kz.afm.candidate.test.session.dto;

import kz.afm.candidate.test.question.QuestionEntity;
import kz.afm.candidate.test.session.answer.TestSessionAnswerEntity;
import kz.afm.candidate.test.session.evaluation.assessment.AssessmentEntity;
import kz.afm.candidate.test.session.evaluation.result.ResultEntity;
import kz.afm.candidate.test.session.evaluation.scale.ScaleEntity;
import kz.afm.candidate.test.session.evaluation.section.SectionEntity;

public class TestSessionResultDto {
    public String questionNameRus;
    public String questionNameKaz;
    public String answer;
    public String assessment;
    public int score;
    public String scaleNameRus;
    public String scaleNameKaz;
    public String descriptionRus;
    public String descriptionKaz;
    public String fileUrl;

    public TestSessionResultDto(AssessmentEntity assessment) {
        QuestionEntity question = assessment.getTestSessionAnswer().getQuestion();
        this.questionNameRus = question.nameRus ;
        this.questionNameKaz = question.nameKaz;

        TestSessionAnswerEntity answer = assessment.getTestSessionAnswer();
        this.answer = answer.getAnswer();

        this.assessment = assessment.getResult();

        this.score = -1;
        this.scaleNameRus = null;
        this.scaleNameKaz = null;
        this.descriptionRus = null;
        this.descriptionKaz = null;
    }

    public TestSessionResultDto(ResultEntity result, SectionEntity section) {
        this.questionNameRus = null;
        this.questionNameKaz = null;
        this.answer = null;
        this.assessment = null;

        this.score = result.score;

        ScaleEntity scale = result.scale;
        this.scaleNameRus = scale.nameRus;
        this.scaleNameKaz = scale.nameKaz;

        this.descriptionRus = section.descriptionRus;
        this.descriptionKaz = section.descriptionKaz;
    }
}
