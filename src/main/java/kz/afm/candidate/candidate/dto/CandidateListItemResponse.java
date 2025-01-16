package kz.afm.candidate.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CandidateListItemResponse {
    private String identificationNumber;
    private String lastName;
    private String firstName;
    private String middleName;
}
