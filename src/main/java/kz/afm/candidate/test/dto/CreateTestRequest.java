package kz.afm.candidate.test.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kz.afm.candidate.test.dto.evaluation.ConditionalSectioningVariableDto;
import kz.afm.candidate.test.dto.evaluation.CreateScaleRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class CreateTestRequest {

    @NotNull(message = "название теста (рус) обязательно")
    @NotEmpty(message = "название теста (рус) обязательно")
    public String nameRus;

    @NotNull(message = "название теста (каз) обязательно")
    @NotEmpty(message = "название теста (каз) обязательно")
    public String nameKaz;

    public String descriptionRus;
    public String descriptionKaz;
    public String isLimitless;
    public int duration;

    @NotNull(message = "варианты теста обязательны")
    public List<CreateVariantRequest> variants;

    @NotNull(message = "направления теста обязательны")
    public List<String> areasOfActivities;

    public int type;
    public int maxPointsPerQuestion;
    public boolean conditionallySectioned;
    public List<ConditionalSectioningVariableDto> conditionalVars;

    @NotNull(message = "шкалы теста обязательны")
    public List<CreateScaleRequest> scales;
}
