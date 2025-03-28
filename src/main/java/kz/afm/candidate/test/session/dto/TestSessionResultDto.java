package kz.afm.candidate.test.session.dto;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.http.Method;
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
        this.questionNameRus = question.getNameRus();
        this.questionNameKaz = question.getNameKaz();

        TestSessionAnswerEntity answer = assessment.getTestSessionAnswer();
        this.answer = answer.getAnswer();

        this.assessment = assessment.getResult();

        this.score = -1;
        this.scaleNameRus = null;
        this.scaleNameKaz = null;
        this.descriptionRus = null;
        this.descriptionKaz = null;

        if (isFileAnswer(this.answer)) {
            this.fileUrl = generateMinioPresignedUrl(this.answer);
        }
    }

    public TestSessionResultDto(ResultEntity result, SectionEntity section) {
        this.questionNameRus = null;
        this.questionNameKaz = null;
        this.answer = null;
        this.assessment = null;

        this.score = result.getScore();

        ScaleEntity scale = result.getScale();
        this.scaleNameRus = scale.getNameRus();
        this.scaleNameKaz = scale.getNameKaz();

        this.descriptionRus = section.getDescriptionRus();
        this.descriptionKaz = section.getDescriptionKaz();
    }

    private boolean isFileAnswer(String answer) {
        if (answer == null) return false;
        String lower = answer.toLowerCase();
        return lower.endsWith(".jpg") || lower.endsWith(".jpeg") || lower.endsWith(".png")
                || lower.endsWith(".pdf") || lower.endsWith(".docx") || lower.endsWith(".xlsx");
    }

    private String generateMinioPresignedUrl(String fileName) {
        try {
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://localhost:9000/")
                    .credentials("minioadmin", "minioadmin")
                    .build();

            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket("files")
                            .object(fileName)
                            .expiry(60 * 60)
                            .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
