package kz.afm.candidate.test.variant;

import kz.afm.candidate.test.TestEntity;
import kz.afm.candidate.test.dto.CreateVariantRequest;
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

}
