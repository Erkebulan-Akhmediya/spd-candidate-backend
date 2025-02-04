package kz.afm.candidate.candidate.education.type.dto;

import kz.afm.candidate.candidate.education.type.EducationTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class EducationTypeResponseBody {
    private int id;
    private String nameRus;
    private String nameKaz;

    public static EducationTypeResponseBody fromEntity(EducationTypeEntity type) {
        return new EducationTypeResponseBody(
                type.getId(),
                type.getNameRus(),
                type.getNameKaz()
        );
    }

    public static List<EducationTypeResponseBody> fromEntities(List<EducationTypeEntity> types) {
        return types.stream().map(EducationTypeResponseBody::fromEntity).toList();
    }

}
