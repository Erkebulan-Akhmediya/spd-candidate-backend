package kz.afm.candidate.test.dto.evaluation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class SectioningConditionDto {
    public String varName;
    public String operator;
    public List<String> value;
}
