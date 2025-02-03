package kz.afm.candidate.test.question;

import kz.afm.candidate.dto.ResponseBodyWrapper;
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

import java.util.NoSuchElementException;
import java.util.Set;

@RequiredArgsConstructor
@RequestMapping("test/question")
@RestController
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionResponseBodyFactory questionResponseBodyFactory;
    private final OptionService optionService;
    private final OptionResponseBodyFactory optionResponseBodyFactory;

    @GetMapping("{id}")
    public ResponseEntity<ResponseBodyWrapper<QuestionResponseBody>> getById(@PathVariable long id) {
        try {
            final QuestionEntity question = this.questionService.getById(id);

            final Set<OptionResponseBody> options = this.optionResponseBodyFactory
                    .createSet(this.optionService.getAllByQuestion(question));

            final QuestionResponseBody questionResponseBody = this.questionResponseBodyFactory.create(question, options);

            return ResponseEntity.ok(ResponseBodyWrapper.success(questionResponseBody));

        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(ResponseBodyWrapper.error(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

}
