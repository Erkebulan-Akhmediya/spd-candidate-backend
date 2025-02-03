package kz.afm.candidate.test.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OptionResponseBody {
    private long id;
    private String nameRus;
    private String nameKaz;
    private boolean withFile;
    private String fileUrl;

    @JsonProperty("isCorrect")
    private Boolean isCorrect;
}
