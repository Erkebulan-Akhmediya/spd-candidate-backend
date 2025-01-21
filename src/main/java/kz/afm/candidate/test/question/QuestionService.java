package kz.afm.candidate.test.question;

import kz.afm.candidate.test.dto.CreateQuestionRequest;
import kz.afm.candidate.test.option.OptionService;
import kz.afm.candidate.test.question.type.QuestionTypeService;
import kz.afm.candidate.test.variant.VariantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final OptionService optionService;
    private final QuestionTypeService questionTypeService;
    private final QuestionRepository questionRepository;

    public void create(VariantEntity variant, List<CreateQuestionRequest> dtos) throws NoSuchElementException {
        dtos.forEach((CreateQuestionRequest dto) -> {
            final QuestionEntity question = this.questionRepository.save(
                    new QuestionEntity(
                            dto.isWithFile(),
                            UUID.randomUUID(),
                            dto.getNameRus(),
                            dto.getNameKaz(),
                            this.questionTypeService.getById(dto.getType()),
                            variant
                    )
            );
            this.optionService.create(question, dto.getOptions());
        });
    }

}
