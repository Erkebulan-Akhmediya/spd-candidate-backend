package kz.afm.candidate.test.question;

import kz.afm.candidate.file.FileService;
import kz.afm.candidate.test.dto.CreateQuestionRequest;
import kz.afm.candidate.test.option.OptionService;
import kz.afm.candidate.test.question.type.QuestionTypeService;
import kz.afm.candidate.test.variant.VariantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final OptionService optionService;
    private final QuestionTypeService questionTypeService;
    private final FileService fileService;

    private final QuestionRepository questionRepository;

    public void create(VariantEntity variant, List<CreateQuestionRequest> dtos) throws NoSuchElementException {
        dtos.forEach((CreateQuestionRequest dto) -> {
            try {
                final String fileName = dto.isWithFile() ? this.fileService.save(dto.getFile()) : null;
                final QuestionEntity question = this.questionRepository.save(
                        new QuestionEntity(
                                dto.isWithFile(),
                                fileName,
                                dto.getNameRus(),
                                dto.getNameKaz(),
                                this.questionTypeService.getById(dto.getType()),
                                variant
                        )
                );
                this.optionService.create(question, dto.getOptions());
            } catch (NoSuchElementException e) {
                throw e;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Ошибка сохранения файла");
            }
        });
    }

    public long getCountByVariant(VariantEntity variant) {
        return this.questionRepository.countByVariant(variant);
    }

}
