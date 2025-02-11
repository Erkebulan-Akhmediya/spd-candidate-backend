package kz.afm.candidate.test.question.dto;

import kz.afm.candidate.file.FileService;
import kz.afm.candidate.test.question.QuestionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class QuestionResponseBodyFactory {

    private final FileService fileService;

    public QuestionResponseBody create(QuestionEntity question, List<OptionResponseBody> options) {
        return new QuestionResponseBody(
                question.getId(),
                question.getNameRus(),
                question.getNameKaz(),
                question.isWithFile(),
                question.isWithFile() ? this.fileService.getBase64Url(question.getFileName()) : null,
                options
        );
    }

}
