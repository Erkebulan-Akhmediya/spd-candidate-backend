package kz.afm.candidate.reference.nationality.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetAllNationalityResponse {
    private String error;
    private List<NationalityResponse> nationalities;
}
