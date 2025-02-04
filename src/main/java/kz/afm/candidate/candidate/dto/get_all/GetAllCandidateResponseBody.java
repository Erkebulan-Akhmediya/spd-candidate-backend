package kz.afm.candidate.candidate.dto.get_all;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetAllCandidateResponseBody {
    private List<CandidateListItemResponse> candidates;
    private long count;
}
