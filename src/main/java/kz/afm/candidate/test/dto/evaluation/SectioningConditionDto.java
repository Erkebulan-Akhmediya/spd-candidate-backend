package kz.afm.candidate.test.dto.evaluation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kz.afm.candidate.test.session.evaluation.section.conditional.condition.SectioningConditionEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class SectioningConditionDto {

    @NotNull(message = "название переменной обязательно в условии")
    @NotEmpty(message = "название переменной обязательно в условии")
    public String varName;

    @NotNull(message = "оператор обязателен в условии")
    @NotEmpty(message = "оператор обязателен в условии")
    public String operator;

    @NotNull(message = "значение переменной обязательно в условии")
    public List<String> value;

    public SectioningConditionDto(SectioningConditionEntity condition) {
        this.varName = condition.variable.name;
        this.operator = condition.operator;
        this.value = condition.value;
    }

}
