package kz.afm.candidate.reference.language.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetAllLanguagesResponse {
    private String error;
    private List<LanguageResponse> languages;
}
