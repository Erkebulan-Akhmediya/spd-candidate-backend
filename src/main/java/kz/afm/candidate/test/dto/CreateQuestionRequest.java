package kz.afm.candidate.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateQuestionRequest {
    private boolean withFile;
    private String fileName;
    private String nameRus;
    private String nameKaz;
    private int type;
    private List<CreateOptionRequest> options;
}
