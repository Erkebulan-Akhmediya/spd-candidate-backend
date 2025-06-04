package kz.afm.candidate.test.dto.evaluation;

import kz.afm.candidate.test.session.evaluation.section.SectionEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ScaleSectionDto {
    public int index;
    public int upperBound;
    public int lowerBound;
    public String descriptionRus;
    public String descriptionKaz;
    public List<SectioningConditionDto> conditions;

    public ScaleSectionDto(int index, SectionEntity section, List<SectioningConditionDto> conditionDtos) {
        this.index = index;
        this.upperBound = section.getUpperBound();
        this.lowerBound = section.getLowerBound();
        this.descriptionRus = section.getDescriptionRus();
        this.descriptionKaz = section.getDescriptionKaz();
        this.conditions = conditionDtos;
    }

}
