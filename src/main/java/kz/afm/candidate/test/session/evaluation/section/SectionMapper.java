package kz.afm.candidate.test.session.evaluation.section;

import kz.afm.candidate.test.dto.evaluation.ScaleSectionDto;
import kz.afm.candidate.test.dto.evaluation.SectioningConditionDto;
import kz.afm.candidate.test.session.evaluation.section.conditional.condition.SectioningConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SectionMapper {

    private final SectioningConditionService sectioningConditionService;

    public ScaleSectionDto toDto(int index, SectionEntity section) {
        final List<SectioningConditionDto> conditionDtos = this.sectioningConditionService.getAllBySection(section)
                .stream()
                .map(SectioningConditionDto::new)
                .toList();
        return new ScaleSectionDto(index, section, conditionDtos);
    }

}
