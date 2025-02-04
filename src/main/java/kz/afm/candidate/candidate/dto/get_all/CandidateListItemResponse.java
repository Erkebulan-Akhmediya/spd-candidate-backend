package kz.afm.candidate.candidate.dto.get_all;

import kz.afm.candidate.candidate.CandidateEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CandidateListItemResponse {
    private String identificationNumber;
    private String lastName;
    private String firstName;
    private String middleName;

    public static CandidateListItemResponse fromEntity(CandidateEntity candidate) {
        return new CandidateListItemResponse(
                candidate.getIdentificationNumber(),
                candidate.getLastName(),
                candidate.getFirstName(),
                candidate.getMiddleName()
        );
    }

    public static List<CandidateListItemResponse> fromEntities(List<CandidateEntity> candidates) {
        return candidates.stream().map(CandidateListItemResponse::fromEntity).toList();
    }

}
