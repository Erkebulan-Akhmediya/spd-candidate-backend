package kz.afm.candidate.test.question;

import kz.afm.candidate.dto.ResponseBodyWrapper;
import kz.afm.candidate.file.FileService;
import kz.afm.candidate.test.option.OptionEntity;
import kz.afm.candidate.test.option.OptionService;
import kz.afm.candidate.test.question.dto.OptionResponseBody;
import kz.afm.candidate.test.question.dto.QuestionResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RequestMapping("test/question")
@RestController
public class QuestionController {

    private final QuestionService questionService;
    private final OptionService optionService;
    private final FileService fileService;

    @GetMapping("{id}")
    public ResponseEntity<ResponseBodyWrapper<QuestionResponseBody>> getByIdAndSend(@PathVariable long id) {
        try {
            return ResponseEntity.ok(this.getById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(ResponseBodyWrapper.error(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

    private ResponseBodyWrapper<QuestionResponseBody> getById(long id) {
        final QuestionEntity question = this.questionService.getById(id);
        final List<OptionEntity> options = this.optionService.getAllByQuestion(question);
        final List<OptionResponseBody> optionsResponseBody = this.createOptionsResponse(options);
        final QuestionResponseBody questionResponseBody = new QuestionResponseBody(
                question,
                optionsResponseBody,
                question.withFile ? this.fileService.getBase64Url(question.fileName) : null
        );
        return ResponseBodyWrapper.success(questionResponseBody);
    }

    private List<OptionResponseBody> createOptionsResponse(List<OptionEntity> options) {
        return options.stream()
                .map(
                        (OptionEntity option) -> new OptionResponseBody(
                                option,
                                option.isWithFile() ? this.fileService.getBase64Url(option.getFileName()) : null
                        )
                )
                .toList();
    }

}
