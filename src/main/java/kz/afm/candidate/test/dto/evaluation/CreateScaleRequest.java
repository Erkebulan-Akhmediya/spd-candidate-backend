package kz.afm.candidate.test.dto.evaluation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class CreateScaleRequest {
    public int index;
    public String nameRus;
    public String nameKaz;
    public List<CreateScaleSectionRequest> sections;
}
