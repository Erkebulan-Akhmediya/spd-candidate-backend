package kz.afm.candidate.test.dto.evaluation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CreateOptionIncrementRequest {
    public int index;
    public int scaleIndex;
    public int score;
}
