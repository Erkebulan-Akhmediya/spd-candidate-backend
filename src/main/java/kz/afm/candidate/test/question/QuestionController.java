package kz.afm.candidate.test.question;

import kz.afm.candidate.file.FileService;
import kz.afm.candidate.test.option.OptionEntity;
import kz.afm.candidate.test.option.OptionService;
import kz.afm.candidate.test.question.dto.OptionResponse;
import kz.afm.candidate.test.question.dto.QuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("test/question")
@RestController
public class QuestionController {

    private final QuestionService questionService;
    private final FileService fileService;
    private final OptionService optionService;

    @GetMapping("{id}")
    public ResponseEntity<QuestionResponse> getById(@PathVariable long id) {
        try {
            final QuestionEntity question = this.questionService.getById(id);
            final String questionFile = question.isWithFile() ? this.fileService.getBase64(question.getFileName()) : null;

            final Set<OptionResponse> options = this.optionService.getAllByQuestion(question)
                    .stream()
                    .map(
                            (OptionEntity option) -> new OptionResponse(
                                    option.getId(),
                                    option.getNameRus(),
                                    option.getNameKaz(),
                                    option.isWithFile(),
                                    option.isWithFile() ? this.fileService.getBase64(option.getFileName()) : null,
                                    option.getIsCorrect()
                            )
                    )
                    .collect(Collectors.toSet());

            final QuestionResponse questionResponse = new QuestionResponse(
                    null,
                    question.getId(),
                    question.getNameRus(),
                    question.getNameKaz(),
                    question.isWithFile(),
                    questionFile,
                    question.getType().getId(),
                    options
            );

            return ResponseEntity.ok(questionResponse);

        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(new QuestionResponse(e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(new QuestionResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

}
