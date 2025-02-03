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

    public static LanguageResponse fromEntity(LanguageEntity language) {
        return new LanguageResponse(language.getCode(), language.getNameRus(), language.getNameKaz());
    }
}
