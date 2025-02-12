package kz.afm.candidate.test.dto;

import kz.afm.candidate.test.dto.evaluation.CreateScaleRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTestRequest {
    private String nameRus;
    private String nameKaz;
    private String isLimitless;
    private int duration;
    private List<CreateVariantRequest> variants;
    private List<String> areasOfActivities;
    private int type;
    private int maxPointsPerQuestion;
    private List<CreateScaleRequest> scales;
}
