package kz.afm.candidate.test.question;

import kz.afm.candidate.test.dto.CreateQuestionRequest;
import kz.afm.candidate.test.option.OptionService;
import kz.afm.candidate.test.variant.VariantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final OptionService optionService;

    private final QuestionRepository questionRepository;

    public void create(VariantEntity variant, List<CreateQuestionRequest> dtos) throws NoSuchElementException {
        dtos.forEach((CreateQuestionRequest dto) -> {
            try {
                final QuestionEntity question = this.questionRepository.save(
                        new QuestionEntity(
                                dto.isWithFile(),
                                dto.getFileName(),
                                dto.getNameRus(),
                                dto.getNameKaz(),
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

    public Set<Long> getIdsByVariant(VariantEntity variant) throws NoSuchElementException {
        final List<QuestionEntity> questions = this.getByVariant(variant);
        return this.extractIds(questions);
    }

    public List<QuestionEntity> getByVariant(VariantEntity variant) throws NoSuchElementException {
        final List<QuestionEntity> questions = this.questionRepository.findAllByVariant(variant, Sort.by("id"));
        if (questions.isEmpty()) {
            throw new NoSuchElementException("Вопросы для варината с ID: " + variant.getId() + " не найдены");
        }
        return questions;
    }

    public QuestionEntity getById(Long id) throws NoSuchElementException {
        return this.questionRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Вопрос с ID: " + id + "не найден")
        );
    }

    public Set<Long> extractIds(List<QuestionEntity> questions) {
        return questions.stream().map(QuestionEntity::getId).collect(Collectors.toSet());
    }

}
