package kz.afm.candidate.test.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class OptionResponseBody {
    public long id;
    public String nameRus;
    public String nameKaz;
    public boolean withFile;
    public String fileUrl;

    @JsonProperty("isCorrect")
    public Boolean isCorrect;
}
