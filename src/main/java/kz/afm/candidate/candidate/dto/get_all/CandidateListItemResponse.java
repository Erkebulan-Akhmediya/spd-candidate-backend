package kz.afm.candidate.candidate.dto.get_all;

import kz.afm.candidate.candidate.CandidateEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CandidateListItemResponse {
    public String identificationNumber;
    public String lastName;
    public String firstName;
    public String middleName;

    public CandidateListItemResponse(CandidateEntity candidate) {
        this.identificationNumber = candidate.getIdentificationNumber();
        this.lastName = candidate.getLastName();
        this.firstName = candidate.getFirstName();
        this.middleName = candidate.getMiddleName();
    }

}
