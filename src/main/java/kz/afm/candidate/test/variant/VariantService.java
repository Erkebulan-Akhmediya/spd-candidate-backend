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

    public void create(TestEntity test, List<CreateVariantRequest> dtos) throws NoSuchElementException {
        dtos.forEach((CreateVariantRequest dto) -> {
            final VariantEntity variant = this.variantRepository.save(new VariantEntity(test));
            this.questionService.create(variant, dto.getQuestions());
        });
    }

}
