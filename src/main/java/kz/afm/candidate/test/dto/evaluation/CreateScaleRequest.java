package kz.afm.candidate.test.dto.evaluation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateScaleRequest {
    private int index;
    private String nameRus;
    private String nameKaz;
    private List<CreateScaleSectionRequest> sections;
}
