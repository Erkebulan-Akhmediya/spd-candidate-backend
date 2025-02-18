package kz.afm.candidate.candidate.education.type.dto;

import kz.afm.candidate.candidate.education.type.EducationTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EducationTypeResponseBody {
    private int id;
    private String nameRus;
    private String nameKaz;

    public EducationTypeResponseBody(EducationTypeEntity educationTypeEntity) {
        this.id = educationTypeEntity.getId();
        this.nameRus = educationTypeEntity.getNameRus();
        this.nameKaz = educationTypeEntity.getNameKaz();
    }

}
