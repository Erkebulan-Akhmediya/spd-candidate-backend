package kz.afm.candidate.candidate.dto;

import kz.afm.candidate.candidate.status.CandidateStatusEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CandidateStatusResponse {
    private int id;
    private String nameRus;
    private String nameKaz;

    public static CandidateStatusResponse fromEntity(CandidateStatusEntity status) {
        return new CandidateStatusResponse(status.getId(), status.getNameRus(), status.getNameKaz());
    }
}
