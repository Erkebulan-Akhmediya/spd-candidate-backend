package kz.afm.candidate.candidate.dto.get_all;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class GetAllCandidateResponseBody {
    public List<CandidateListItemResponse> candidates;
    public long count;
}
