package kz.afm.candidate.test.question.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kz.afm.candidate.test.option.OptionEntity;
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

    public OptionResponseBody(OptionEntity option, String fileUrl) {
        this.id = option.getId();
        this.nameRus = option.getNameRus();
        this.nameKaz = option.getNameKaz();
        this.withFile = option.isWithFile();
        this.fileUrl = fileUrl;
        this.isCorrect = option.getIsCorrect();
    }
}
