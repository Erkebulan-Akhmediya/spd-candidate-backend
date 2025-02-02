package kz.afm.candidate.candidate.dto;

import kz.afm.candidate.candidate.CandidateEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
}
