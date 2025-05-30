package kz.afm.candidate.candidate;

import kz.afm.candidate.candidate.dto.*;
import kz.afm.candidate.candidate.dto.get_all.CandidateListItemResponse;
import kz.afm.candidate.candidate.dto.get_all.GetAllCandidateResponseBody;
import kz.afm.candidate.candidate.dto.CandidateResponseDto;
import kz.afm.candidate.candidate.education.EducationEntity;
import kz.afm.candidate.candidate.education.EducationService;
import kz.afm.candidate.candidate.experience.ExperienceEntity;
import kz.afm.candidate.candidate.experience.ExperienceService;
import kz.afm.candidate.candidate.language_knowledge.LanguageKnowledgeEntity;
import kz.afm.candidate.candidate.language_knowledge.LanguageKnowledgeService;
import kz.afm.candidate.dto.ResponseBodyWrapper;
import kz.afm.candidate.test.session.TestSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RequestMapping("candidate")
@RestController
public class CandidateController {

    private final CandidateService candidateService;
    private final ExperienceService experienceService;
    private final EducationService educationService;
    private final LanguageKnowledgeService languageKnowledgeService;
    private final TestSessionService testSessionService;

    @PostMapping
    public ResponseEntity<ResponseBodyWrapper<Void>> create(@RequestBody CandidateRequestDto candidate) {
        try {
            this.candidateService.create(candidate);
            return ResponseEntity.ok(ResponseBodyWrapper.success());
        } catch (NoSuchElementException | NullPointerException e) {
            return ResponseEntity.badRequest().body(ResponseBodyWrapper.error(e.getMessage()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка на сервере"));
        }
    }

    @PutMapping
    public ResponseEntity<ResponseBodyWrapper<Void>> update(@RequestBody CandidateRequestDto candidate) {
        try {
            this.candidateService.update(candidate);
            return ResponseEntity.ok(ResponseBodyWrapper.success());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        }
    }

    @GetMapping("all")
    public ResponseEntity<ResponseBodyWrapper<GetAllCandidateResponseBody>> getAll(
            @RequestParam int statusId,
            @RequestParam int regionId,
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    ) {
        try {
            final List<CandidateEntity> candidateEntities = this.candidateService
                    .getAll(statusId, regionId, pageNumber, pageSize);

            final List<CandidateListItemResponse> candidates = candidateEntities
                    .stream()
                    .map(CandidateListItemResponse::new)
                    .toList();

            final long count = this.candidateService.countAll(statusId, regionId);

            return ResponseEntity.ok(ResponseBodyWrapper.success(new GetAllCandidateResponseBody(candidates, count)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    ResponseBodyWrapper.error("Ошибка сервера")
            );
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseBodyWrapper<CandidateResponseDto>> getById(@PathVariable String id) {
        try {
            final CandidateEntity candidate = this.candidateService.getById(id);
            final List<ExperienceEntity> experiences = this.experienceService.getByCandidate(candidate);
            final List<EducationEntity> education = this.educationService.getAllByCandidate(candidate);
            final List<LanguageKnowledgeEntity> languageKnowledge = this.languageKnowledgeService.getAllByCandidate(candidate);
            final CandidateResponseDto candidateDto = new CandidateResponseDto(candidate, experiences, education, languageKnowledge);
            return ResponseEntity.ok(ResponseBodyWrapper.success(candidateDto));
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

    @PutMapping("reject/{iin}")
    public ResponseEntity<ResponseBodyWrapper<Void>> reject(@PathVariable String iin) {
        try {
            this.candidateService.reject(iin);
            return ResponseEntity.ok(ResponseBodyWrapper.success());
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка на сервере"));
        }
    }

    @PutMapping("to/security")
    public ResponseEntity<ResponseBodyWrapper<Void>> sendToSecurityCheck(@RequestBody CandidateRequestDto candidate) {
        try {
            this.candidateService.sendToSecurityCheck(candidate);
            return ResponseEntity.ok(ResponseBodyWrapper.success());
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка на сервере"));
        }
    }

    @PutMapping("to/approval")
    public ResponseEntity<ResponseBodyWrapper<Void>> sendToApproval(@RequestBody CandidateRequestDto candidate) {
        try {
            this.candidateService.sendToApproval(candidate);
            return ResponseEntity.ok(ResponseBodyWrapper.success());
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка на сервере"));
        }
    }

    @PutMapping("approve/{iin}")
    public ResponseEntity<ResponseBodyWrapper<Void>> approve(@PathVariable String iin, @RequestParam String areaOfActivity) {
        try {
            this.candidateService.approve(iin, areaOfActivity);
            return ResponseEntity.ok(ResponseBodyWrapper.success());
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка на сервере"));
        }
    }

    @DeleteMapping("{iin}")
    public ResponseEntity<ResponseBodyWrapper<Void>> delete(@PathVariable String iin) {
        try {
            this.testSessionService.deleteByCandidateIdentificationNumber(iin);
            this.candidateService.delete(iin);
            return ResponseEntity.ok(ResponseBodyWrapper.success());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        }
    }

}
