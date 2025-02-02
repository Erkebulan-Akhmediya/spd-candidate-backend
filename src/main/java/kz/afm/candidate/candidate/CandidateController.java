package kz.afm.candidate.candidate;

import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityEntity;
import kz.afm.candidate.candidate.dto.*;
import kz.afm.candidate.candidate.status.CandidateStatusService;
import kz.afm.candidate.candidate.experience.ExperienceService;
import kz.afm.candidate.dto.ResponseBodyFactory;
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
    public ResponseEntity<ResponseBodyFactory<List<CandidateStatusResponse>>> getAllCandidateStatus() {
        try {
            final List<CandidateStatusResponse> statuses = this.candidateStatusService.getAll()
                    .stream()
                    .map(CandidateStatusResponse::fromEntity)
                    .toList();
            return ResponseEntity.ok(ResponseBodyFactory.success(statuses));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseBodyFactory.error("Ошибка сервера"));
        }
    }

    @PostMapping
    public ResponseEntity<ResponseBodyFactory<Void>> create(@RequestBody CandidateRequest candidate) {
        try {
            this.candidateService.create(candidate);
            return ResponseEntity.ok(ResponseBodyFactory.success());
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(ResponseBodyFactory.error(e.getMessage()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseBodyFactory.error("Ошибка на сервере"));
        }
    }

    @GetMapping("all")
    public ResponseEntity<ResponseBodyFactory<GetAllCandidateResponse>> getAll(
            @RequestParam int statusId,
            @RequestParam int regionId,
            @RequestParam int pageNumber,
            @RequestParam int pageSize
    ) {
        try {
            final List<CandidateListItemResponse> candidates = this.candidateService
                    .getAll(statusId, regionId, pageNumber, pageSize)
                    .stream()
                    .map(CandidateListItemResponse::fromEntity)
                    .toList();
            final long count = this.candidateService.countAll(statusId, regionId);
            return ResponseEntity.ok(ResponseBodyFactory.success(new GetAllCandidateResponse(candidates, count)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    ResponseBodyFactory.error("Ошибка сервера")
            );
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseBodyFactory<CandidateResponse>> getById(@PathVariable String id) {
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
                    .map(ExperienceDto::fromEntity)
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

            final AreaOfActivityEntity areaOfActivity = candidate.getAreaOfActivity();
            if (areaOfActivity != null) {
                candidateResponse.setAreaOfActivity(areaOfActivity.getName());
            }

            return ResponseEntity.ok(ResponseBodyFactory.success(candidateResponse));
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(
                    ResponseBodyFactory.error(e.getMessage())
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    ResponseBodyFactory.error("Ошибка сервера")
            );
        }
    }

    @PutMapping("reject/{iin}")
    public ResponseEntity<ResponseBodyFactory<Void>> reject(@PathVariable String iin) {
        try {
            this.candidateService.reject(iin);
            return ResponseEntity.ok(ResponseBodyFactory.success());
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(ResponseBodyFactory.error(e.getMessage()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseBodyFactory.error("Ошибка на сервере"));
        }
    }

    @PutMapping("to/security")
    public ResponseEntity<ResponseBodyFactory<Void>> sendToSecurityCheck(@RequestBody CandidateRequest candidate) {
        try {
            this.candidateService.sendToSecurityCheck(candidate);
            return ResponseEntity.ok(ResponseBodyFactory.success());
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(ResponseBodyFactory.error(e.getMessage()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseBodyFactory.error("Ошибка на сервере"));
        }
    }

    @PutMapping("to/approval")
    public ResponseEntity<ResponseBodyFactory<Void>> sendToApproval(@RequestBody CandidateRequest candidate) {
        try {
            this.candidateService.sendToApproval(candidate);
            return ResponseEntity.ok(ResponseBodyFactory.success());
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(ResponseBodyFactory.error(e.getMessage()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseBodyFactory.error("Ошибка на сервере"));
        }
    }

    @PutMapping("approve/{iin}")
    public ResponseEntity<ResponseBodyFactory<Void>> approve(@PathVariable String iin, @RequestParam String areaOfActivity) {
        try {
            this.candidateService.approve(iin, areaOfActivity);
            return ResponseEntity.ok(ResponseBodyFactory.success());
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(ResponseBodyFactory.error(e.getMessage()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(ResponseBodyFactory.error("Ошибка на сервере"));
        }
    }

}
