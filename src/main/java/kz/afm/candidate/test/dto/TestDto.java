package kz.afm.candidate.test.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityEntity;
import kz.afm.candidate.test.TestEntity;
import kz.afm.candidate.test.dto.evaluation.ConditionalSectioningVariableDto;
import kz.afm.candidate.test.dto.evaluation.ScaleDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class TestDto {

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
    public List<VariantDto> variants;

    @NotNull(message = "направления теста обязательны")
    public List<String> areasOfActivities;

    public int type;
    public int maxPointsPerQuestion;
    public boolean conditionallySectioned;
    public List<ConditionalSectioningVariableDto> conditionalVars;

    @NotNull(message = "шкалы теста обязательны")
    public List<ScaleDto> scales;

    public TestDto(
            TestEntity test,
            List<VariantDto> variantDtos,
            int maxPointsPerQuestion,
            List<ConditionalSectioningVariableDto> conditionalVarDtos,
            List<ScaleDto> scaleDtos
    ) {
        this.nameRus = test.getNameRus();
        this.nameKaz = test.getNameKaz();
        this.descriptionRus = test.getDescriptionRus();
        this.descriptionKaz = test.getDescriptionKaz();
        this.isLimitless = String.valueOf(test.isLimitless());
        this.duration = test.getDuration();
        this.variants = variantDtos;
        this.areasOfActivities = test.getAreaOfActivities().stream().map(AreaOfActivityEntity::getName).toList();
        this.type = test.getType().getId();
        this.maxPointsPerQuestion = maxPointsPerQuestion;
        this.conditionallySectioned = test.isConditionallySectioned();
        this.conditionalVars = conditionalVarDtos;
        this.scales = scaleDtos;
    }

}
