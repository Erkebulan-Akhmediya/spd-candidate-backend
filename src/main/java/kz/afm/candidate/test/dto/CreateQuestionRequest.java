package kz.afm.candidate.test.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class CreateQuestionRequest {
    public boolean withFile;
    public String fileName;
    public String nameRus;
    public String nameKaz;
    public boolean isDisappearing;
    public int timeToDisappear;
    public List<CreateOptionRequest> options;
}
