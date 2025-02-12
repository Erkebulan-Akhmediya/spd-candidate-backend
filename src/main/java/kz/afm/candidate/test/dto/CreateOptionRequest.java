package kz.afm.candidate.test.dto;

import kz.afm.candidate.test.dto.evaluation.CreateOptionIncrementRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOptionRequest {
    private boolean withFile;
    private String fileName;
    private String nameRus;
    private String nameKaz;
    private Boolean isCorrect;
    private CreateOptionIncrementRequest increment;
}
