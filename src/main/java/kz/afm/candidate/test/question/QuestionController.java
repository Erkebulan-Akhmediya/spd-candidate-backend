package kz.afm.candidate.test.question;

import kz.afm.candidate.dto.ResponseBodyWrapper;
import kz.afm.candidate.test.option.OptionEntity;
import kz.afm.candidate.test.option.OptionService;
import kz.afm.candidate.test.question.dto.OptionResponseBody;
import kz.afm.candidate.test.question.dto.OptionResponseBodyFactory;
import kz.afm.candidate.test.question.dto.QuestionResponseBody;
import kz.afm.candidate.test.question.dto.QuestionResponseBodyFactory;
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
    private final QuestionResponseBodyFactory questionResponseBodyFactory;
    private final OptionService optionService;
    private final OptionResponseBodyFactory optionResponseBodyFactory;

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
        final List<OptionResponseBody> optionsResponseBody = this.optionResponseBodyFactory.createList(options);
        final QuestionResponseBody questionResponseBody = this.questionResponseBodyFactory.create(question, optionsResponseBody);
        return ResponseBodyWrapper.success(questionResponseBody);
    }

}
