package kz.afm.candidate.candidate;

import kz.afm.candidate.candidate.dto.CandidateStatusResponse;
import kz.afm.candidate.candidate.status.CandidateStatusEntity;
import kz.afm.candidate.candidate.status.CandidateStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("candidate")
@RestController
public class CandidateController {

    private final CandidateStatusService candidateStatusService;

    @GetMapping("status/all")
    public ResponseEntity<List<CandidateStatusResponse>> getAllCandidateStatus() {
        try {
            final List<CandidateStatusResponse> statuses = this.candidateStatusService.getAll()
                    .stream()
                    .map(
                            (CandidateStatusEntity status) -> new CandidateStatusResponse(
                                    status.getId(),
                                    status.getNameRus(),
                                    status.getNameKaz()
                            )
                    )
                    .toList();
            return ResponseEntity.ok(statuses);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
