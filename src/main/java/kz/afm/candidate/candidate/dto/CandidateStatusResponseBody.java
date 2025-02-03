package kz.afm.candidate.candidate.dto;

import kz.afm.candidate.candidate.status.CandidateStatusEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CandidateStatusResponseBody {
    private int id;
    private String nameRus;
    private String nameKaz;

    public static CandidateStatusResponseBody fromEntity(CandidateStatusEntity status) {
        return new CandidateStatusResponseBody(status.getId(), status.getNameRus(), status.getNameKaz());
    }

    public static List<CandidateStatusResponseBody> fromEntities(List<CandidateStatusEntity> status) {
        return status.stream().map(CandidateStatusResponseBody::fromEntity).toList();
    }

}
