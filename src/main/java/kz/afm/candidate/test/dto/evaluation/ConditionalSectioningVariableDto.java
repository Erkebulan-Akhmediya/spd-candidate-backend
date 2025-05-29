package kz.afm.candidate.test.dto.evaluation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ConditionalSectioningVariableDto {
    public String name;
    public int type;
    public List<String> reference;
}
