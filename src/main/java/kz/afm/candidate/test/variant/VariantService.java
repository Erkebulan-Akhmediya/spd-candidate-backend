package kz.afm.candidate.test.variant;

import kz.afm.candidate.test.TestEntity;
import kz.afm.candidate.test.dto.CreateVariantRequest;
import kz.afm.candidate.test.question.QuestionEntity;
import kz.afm.candidate.test.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class VariantService {

    private final QuestionService questionService;
    private final VariantRepository variantRepository;

    public void create(TestEntity test, List<CreateVariantRequest> variantDtoList) throws NoSuchElementException {
        variantDtoList.forEach((CreateVariantRequest variantDto) -> {
            final VariantEntity variant = this.variantRepository.save(new VariantEntity(test));
            this.questionService.create(variant, variantDto.questions);
        });
    }

    public VariantEntity getRandom(long testId) throws NoSuchElementException {
        List<VariantEntity> variants = this.variantRepository.findAllByTest_Id(testId);
        if (variants.isEmpty()) {
            throw new NoSuchElementException("Варианты для теста с ID: " + testId + " не найдены");
        }
        final int randomIndex = (int) (Math.random() * (variants.size() - 1));
        return variants.get(randomIndex);
    }

    public List<QuestionEntity> getQuestionsByTestId(long testId) throws NoSuchElementException {
        final List<VariantEntity> variants = this.getByTestId(testId);
        return this.questionService.getByVariantList(variants);
    }

    public List<VariantEntity> getByTestId(long testId) throws NoSuchElementException {
        return this.variantRepository.findAllByTest_Id(testId);
    }

    public void deleteVariantById(long id) throws NoSuchElementException {
        this.questionService.deleteQuestionByVariantId(id);
        this.variantRepository.deleteById(id);
    }

    public void updateEssayTopicByVariantId(long variantId, String nameRus, String nameKaz) {
        this.questionService.updateEssayTopicByVariantId(variantId, nameRus, nameKaz);
    }

}
