package kz.afm.candidate.test.dto.evaluation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateScaleSectionRequest {
    private int index;
    private int upperBound;
    private int lowerBound;
    private String descriptionRus;
    private String descriptionKaz;
}
