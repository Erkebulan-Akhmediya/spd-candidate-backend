package kz.afm.candidate.reference.language.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LanguageResponse {
    private String code;
    private String nameRus;
    private String nameKaz;
}
