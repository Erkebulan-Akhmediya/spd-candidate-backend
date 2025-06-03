package kz.afm.candidate.test.dto.evaluation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class CreateScaleSectionRequest {
    public int index;
    public int upperBound;
    public int lowerBound;
    public String descriptionRus;
    public String descriptionKaz;
    public List<SectioningConditionDto> conditions;
}
