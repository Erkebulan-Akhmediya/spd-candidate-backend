package kz.afm.candidate.test.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class QuestionResponse {
    private String error;

    private long id;
    private String nameRus;
    private String nameKaz;
    private boolean withFile;
    private String file;
    private int type;
    private Set<OptionResponse> options;

    public QuestionResponse(String error) {
        this.error = error;
    }
}
