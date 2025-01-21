package kz.afm.candidate.candidate;

import kz.afm.candidate.candidate.dto.*;
import kz.afm.candidate.candidate.status.CandidateStatusEntity;
import kz.afm.candidate.candidate.status.CandidateStatusService;
import kz.afm.candidate.candidate.experience.ExperienceEntity;
import kz.afm.candidate.candidate.experience.ExperienceService;
import kz.afm.candidate.reference.driver_license.DriverLicenseEntity;
import kz.afm.candidate.reference.language.LanguageEntity;
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
    private final ExperienceService experienceService;

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
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CandidateRequest candidate) {
        try {
            this.candidateService.create(candidate);
            return ResponseEntity.ok(null);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Ошибка на сервере");
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
            final List<CandidateListItemResponse> candidates = this.candidateService
                    .getAll(statusId, regionId, pageNumber, pageSize)
                    .stream()
                    .map(
                            (CandidateEntity candidate) -> new CandidateListItemResponse(
                                    candidate.getIdentificationNumber(),
                                    candidate.getLastName(),
                                    candidate.getFirstName(),
                                    candidate.getMiddleName()
                            )
                    )
                    .toList();
            final long count = this.candidateService.countAll(statusId, regionId);
            return ResponseEntity.ok(new GetAllCandidateResponse(null, candidates, count));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    new GetAllCandidateResponse("Ошибка сервера", null, 0)
            );
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<CandidateResponse> getById(@PathVariable String id) {
        try {
            final CandidateEntity candidate = this.candidateService.getById(id);
            final List<String> languageCodes = candidate.getLanguages()
                    .stream()
                    .map(LanguageEntity::getCode)
                    .toList();
            final List<String> driverLicenseCodes = candidate.getDriverLicenses()
                    .stream()
                    .map(DriverLicenseEntity::getCode)
                    .toList();
            final List<ExperienceDto> experiences = this.experienceService
                    .getByCandidate(candidate.getIdentificationNumber())
                    .stream()
                    .map(
                            (ExperienceEntity experience) -> new ExperienceDto(
                                    experience.getStartDate(),
                                    experience.getEndDate(),
                                    experience.getPosition(),
                                    experience.getCompanyName()
                            )
                    )
                    .toList();
            final CandidateResponse candidateResponse = CandidateResponse.builder()
                    .lastName(candidate.getLastName())
                    .firstName(candidate.getFirstName())
                    .middleName(candidate.getMiddleName())
                    .birthDate(candidate.getBirthDate())
                    .birthPlace(candidate.getBirthPlace())
                    .testingRegionId(candidate.getTestingRegion().getId())
                    .identificationNumber(candidate.getIdentificationNumber())
                    .phoneNumber(candidate.getPhoneNumber())
                    .nationalityCode(candidate.getNationality().getCode())
                    .languageCodes(languageCodes)
                    .driverLicenseCodes(driverLicenseCodes)
                    .education(candidate.getEducation())
                    .sport(candidate.getSport())
                    .recruitedMethodId(candidate.getRecruitedMethod().getId())
                    .recruitedMethodComment(candidate.getRecruitedMethodComment())
                    .experiences(experiences)
                    .securityCheckResult(candidate.getSecurityCheckResult())
                    .additionalData(candidate.getAdditionalData())
                    .username(candidate.getUser().getUsername())
                    .build();
            return ResponseEntity.ok(candidateResponse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(
                    CandidateResponse.builder().error(e.getMessage()).build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    CandidateResponse.builder().error("Ошибка сервера").build()
            );
        }
    }

    @PutMapping("reject/{iin}")
    public ResponseEntity<String> reject(@PathVariable String iin) {
        try {
            this.candidateService.reject(iin);
            return ResponseEntity.ok("we're so back");
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Ошибка на сервере");
        }
    }

    @PutMapping("to/security")
    public ResponseEntity<String> sendToSecurityCheck(@RequestBody CandidateRequest candidate) {
        try {
            this.candidateService.sendToSecurityCheck(candidate);
            return ResponseEntity.ok("we're so back");
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Ошибка на сервере");
        }
    }

    @PutMapping("to/approval")
    public ResponseEntity<String> sendToApproval(@RequestBody CandidateRequest candidate) {
        try {
            this.candidateService.sendToApproval(candidate);
            return ResponseEntity.ok("we're so back");
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Ошибка на сервере");
        }
    }

    @PutMapping("approve/{iin}")
    public ResponseEntity<String> approve(@PathVariable String iin, @RequestParam String areaOfActivity) {
        try {
            this.candidateService.approve(iin, areaOfActivity);
            return ResponseEntity.ok("we're so back");
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body("Ошибка на сервере");
        }
    }

}
