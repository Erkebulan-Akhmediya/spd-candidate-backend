package kz.afm.candidate.reference.nationality.dto;

import kz.afm.candidate.reference.nationality.NationalityEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NationalityResponse {
    private int code;
    private String nameKaz;
    private String nameRus;

    public static NationalityResponse fromEntity(NationalityEntity nationality) {
        return new NationalityResponse(
                nationality.getCode(),
                nationality.getNameKaz(),
                nationality.getNameRus()
        );
    }
}
