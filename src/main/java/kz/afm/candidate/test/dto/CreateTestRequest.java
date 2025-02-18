package kz.afm.candidate.test.dto;

import kz.afm.candidate.test.dto.evaluation.CreateScaleRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class CreateTestRequest {
    public String nameRus;
    public String nameKaz;
    public String isLimitless;
    public int duration;
    public List<CreateVariantRequest> variants;
    public List<String> areasOfActivities;
    public int type;
    public int maxPointsPerQuestion;
    public List<CreateScaleRequest> scales;
}
