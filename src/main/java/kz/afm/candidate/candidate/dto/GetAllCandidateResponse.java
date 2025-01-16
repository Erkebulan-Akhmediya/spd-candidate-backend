package kz.afm.candidate.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetAllCandidateResponse {
    private String error;
    private List<CandidateResponse> candidates;
    private long count;
}
