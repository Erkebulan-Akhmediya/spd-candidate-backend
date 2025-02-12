package kz.afm.candidate.test.dto.evaluation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOptionIncrementRequest {
    private int index;
    private int scaleIndex;
    private int score;
}
