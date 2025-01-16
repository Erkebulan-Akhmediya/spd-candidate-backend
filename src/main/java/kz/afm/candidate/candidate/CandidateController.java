package kz.afm.candidate.candidate;

import kz.afm.candidate.candidate.dto.*;
import kz.afm.candidate.candidate.status.CandidateStatusEntity;
import kz.afm.candidate.candidate.status.CandidateStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RequestMapping("candidate")
@RestController
public class CandidateController {

    private final CandidateStatusService candidateStatusService;
    private final CandidateService candidateService;

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

    @PostMapping
    public ResponseEntity<CreateCandidateResponse> create(@RequestBody CreateCandidateRequest candidate) {
        try {
            this.candidateService.create(candidate);
            return ResponseEntity.ok(new CreateCandidateResponse(null));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(new CreateCandidateResponse(e.toString()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CreateCandidateResponse("Ошибка на сервере"));
        }
    }

    @GetMapping("all")
    public ResponseEntity<GetAllCandidateResponse> getAll(
            @RequestParam int statusId,
            @RequestParam int regionId,
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    ) {
        try {
            final List<CandidateResponse> candidates = this.candidateService
                    .getAll(statusId, regionId, pageNumber, pageSize)
                    .stream()
                    .map(
                            (CandidateEntity candidate) -> new CandidateResponse(
                                    candidate.getLastName(),
                                    candidate.getFirstName(),
                                    candidate.getMiddleName()
                            )
                    )
                    .toList();
            final long count = this.candidateService.countAll();
            return ResponseEntity.ok(new GetAllCandidateResponse(null, candidates, count));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new GetAllCandidateResponse("Ошибка сервера", null, 0)
            );
        }
    }

}
