package kz.afm.candidate.test.question;

import kz.afm.candidate.test.dto.OptionDto;
import kz.afm.candidate.test.dto.QuestionDto;
import kz.afm.candidate.test.option.OptionEntity;
import kz.afm.candidate.test.option.OptionMapper;
import kz.afm.candidate.test.option.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionMapper {

    private final OptionService optionService;
    private final OptionMapper optionMapper;

    public QuestionDto toDto(QuestionEntity question, List<Long> scaleIds) {
        final List<OptionDto> optionDtos = this.optionService.getAllByQuestion(question)
                .stream()
                .map((OptionEntity option) -> this.optionMapper.toDto(option, scaleIds))
                .toList();
        return new QuestionDto(question, optionDtos);
    }

}
