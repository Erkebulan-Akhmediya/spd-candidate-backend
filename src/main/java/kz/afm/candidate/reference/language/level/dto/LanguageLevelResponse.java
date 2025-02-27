package kz.afm.candidate.reference.language.level.dto;

import kz.afm.candidate.reference.language.level.LanguageLevelEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class LanguageLevelResponse {
    public String code;
    public String nameRus;
    public String nameKaz;

    public LanguageLevelResponse(LanguageLevelEntity level) {
        this.code = level.code;
        this.nameRus = level.nameRus;
        this.nameKaz = level.nameKaz;
    }
}
