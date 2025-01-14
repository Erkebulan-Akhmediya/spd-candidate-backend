package kz.afm.candidate.reference.nationality.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NationalityResponse {
    private int code;
    private String nameKaz;
    private String nameRus;
}
