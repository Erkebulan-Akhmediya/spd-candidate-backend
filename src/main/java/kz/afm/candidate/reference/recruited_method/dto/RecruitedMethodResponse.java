package kz.afm.candidate.reference.recruited_method.dto;

import kz.afm.candidate.reference.recruited_method.RecruitedMethodEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RecruitedMethodResponse {
    private int id;
    private String nameRus;
    private String nameKaz;

    public static RecruitedMethodResponse fromEntity(RecruitedMethodEntity recruitedMethod) {
        return new RecruitedMethodResponse(
                recruitedMethod.getId(),
                recruitedMethod.getNameRus(),
                recruitedMethod.getNameKaz()
        );
    }
}
