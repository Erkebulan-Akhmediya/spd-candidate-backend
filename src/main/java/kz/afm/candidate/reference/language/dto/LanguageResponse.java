package kz.afm.candidate.reference.language.dto;

import kz.afm.candidate.reference.language.LanguageEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LanguageResponse {
    private String code;
    private String nameRus;
    private String nameKaz;

    public LanguageResponse(LanguageEntity languageEntity) {
        this.code = languageEntity.code;
        this.nameRus = languageEntity.nameRus;
        this.nameKaz = languageEntity.nameKaz;
    }

}
