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
                final QuestionEntity newQuestion = new QuestionEntity(
                        dto.withFile,
                        dto.fileName,
                        dto.nameRus,
                        dto.nameKaz,
                        variant
                );
                final QuestionEntity savedQuestion = this.questionRepository.save(newQuestion);
                this.optionService.create(savedQuestion, dto.options);
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

    public List<QuestionEntity> getByVariantList(List<VariantEntity> variantList) throws NoSuchElementException {
        return this.questionRepository.findAllByVariantIn(variantList);
    }

    public QuestionEntity getById(Long id) throws NoSuchElementException {
        return this.questionRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Вопрос с ID: " + id + "не найден")
        );
    }

    public Set<Long> extractIds(List<QuestionEntity> questions) {
        return questions.stream().map(QuestionEntity::getId).collect(Collectors.toSet());
    }

    public void updateEssayTopicByVariantId(long variantId, String nameRus, String nameKaz) {
        final QuestionEntity question = this.questionRepository.findAllByVariant_Id(variantId).getFirst();
        question.setNameRus(nameRus);
        question.setNameKaz(nameKaz);
        this.questionRepository.save(question);
    }

    public void deleteQuestionByVariantId(long variantId) {
        this.questionRepository.deleteByVariant_Id(variantId);
    }

}
