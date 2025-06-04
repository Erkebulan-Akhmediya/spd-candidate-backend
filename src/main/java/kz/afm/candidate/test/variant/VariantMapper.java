package kz.afm.candidate.test.variant;

import kz.afm.candidate.test.dto.QuestionDto;
import kz.afm.candidate.test.dto.VariantDto;
import kz.afm.candidate.test.question.QuestionEntity;
import kz.afm.candidate.test.question.QuestionMapper;
import kz.afm.candidate.test.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class VariantMapper {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    public VariantDto toDto(VariantEntity variant, List<Long> scaleIds) {
        final List<QuestionDto> questionDtos = this.questionService.getByVariant(variant)
                .stream()
                .map((QuestionEntity question) -> this.questionMapper.toDto(question, scaleIds))
                .toList();
        return new VariantDto(questionDtos);
    }

}
