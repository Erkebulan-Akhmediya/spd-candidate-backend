package kz.afm.candidate.test.dto;

import kz.afm.candidate.test.dto.evaluation.CreateOptionIncrementRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class CreateOptionRequest {
    public boolean withFile;
    public String fileName;
    public String nameRus;
    public String nameKaz;
    public Boolean isCorrect;
    public CreateOptionIncrementRequest increment;
}
