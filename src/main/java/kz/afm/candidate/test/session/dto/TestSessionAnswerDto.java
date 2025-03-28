package kz.afm.candidate.test.session.dto;

import kz.afm.candidate.test.question.QuestionEntity;
import kz.afm.candidate.test.session.answer.TestSessionAnswerEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import io.minio.MinioClient;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.http.Method;

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
        this.questionNameRus = question.getNameRus();
        this.questionNameKaz = question.getNameKaz();

        this.answer = answerEntity.getAnswer();
        this.assessment = "";

        if (isFileAnswer(this.answer)) {
            this.fileUrl = generateMinioPresignedUrl(this.answer);
        }
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
