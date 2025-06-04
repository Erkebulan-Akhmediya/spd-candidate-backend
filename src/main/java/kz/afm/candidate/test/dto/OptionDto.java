package kz.afm.candidate.test.dto;

import kz.afm.candidate.test.dto.evaluation.OptionIncrementDto;
import kz.afm.candidate.test.option.OptionEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OptionDto {
    public boolean withFile;
    public String fileName;
    public String nameRus;
    public String nameKaz;
    public Boolean isCorrect;
    public OptionIncrementDto increment;

    public OptionDto(OptionEntity option, OptionIncrementDto increment) {
        this.withFile = option.isWithFile();
        this.fileName = option.getFileName();
        this.nameRus = option.getNameRus();
        this.nameKaz = option.getNameKaz();
        this.isCorrect = option.getIsCorrect();
        this.increment = increment;
    }

}
