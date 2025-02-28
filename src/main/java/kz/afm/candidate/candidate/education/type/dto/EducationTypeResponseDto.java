package kz.afm.candidate.candidate.education.type.dto;

import kz.afm.candidate.candidate.education.type.EducationTypeEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class EducationTypeResponseDto {
    public int id;
    public String nameRus;
    public String nameKaz;

    public EducationTypeResponseDto(EducationTypeEntity educationType) {
        this.id = educationType.id;
        this.nameRus = educationType.nameRus;
        this.nameKaz = educationType.nameKaz;
    }

}
