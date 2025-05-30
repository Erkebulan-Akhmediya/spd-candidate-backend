package kz.afm.candidate.test.dto.evaluation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ConditionalSectioningVariableDto {

    @NotNull(message = "название условной переменной обязательно")
    @NotEmpty(message = "название условной переменной обязательно")
    public String name;

    public int type;

    public List<String> reference;

}
