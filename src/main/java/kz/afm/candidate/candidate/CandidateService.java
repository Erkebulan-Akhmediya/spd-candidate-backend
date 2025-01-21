package kz.afm.candidate.candidate;

import jakarta.transaction.Transactional;
import kz.afm.candidate.candidate.dto.CandidateRequest;
import kz.afm.candidate.candidate.status.CandidateStatusEntity;
import kz.afm.candidate.candidate.status.CandidateStatusService;
import kz.afm.candidate.candidate.experience.ExperienceService;
import kz.afm.candidate.reference.driver_license.DriverLicenseEntity;
import kz.afm.candidate.reference.driver_license.DriverLicenseService;
import kz.afm.candidate.reference.language.LanguageEntity;
import kz.afm.candidate.reference.language.LanguageService;
import kz.afm.candidate.reference.nationality.NationalityEntity;
import kz.afm.candidate.reference.nationality.NationalityService;
import kz.afm.candidate.reference.recruited_method.RecruitedMethodEntity;
import kz.afm.candidate.reference.recruited_method.RecruitedMethodService;
import kz.afm.candidate.reference.region.RegionEntity;
import kz.afm.candidate.reference.region.RegionService;
import kz.afm.candidate.user.UserEntity;
import kz.afm.candidate.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CandidateService {

    private final NationalityService nationalityService;
    private final LanguageService languageService;
    private final DriverLicenseService driverLicenseService;
    private final RecruitedMethodService recruitedMethodService;
    private final ExperienceService experienceService;
    private final UserService userService;
    private final CandidateStatusService candidateStatusService;
    private final RegionService regionService;

    private final CandidateRepository candidateRepository;

    public long countAll(int statusId, int testingRegionId) {
        if (testingRegionId == -1) return this.candidateRepository.countByStatus_Id(statusId);
        return this.candidateRepository.countByStatus_IdAndTestingRegion_Id(statusId, testingRegionId);
    }

    public List<CandidateEntity> getAll(int statusId, int testingRegionId, int pageNumber, int pageSize) {
        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("createDate").descending());
        if (testingRegionId == -1) return this.candidateRepository.findByStatus_Id(statusId, pageRequest);
        return candidateRepository.findByStatus_IdAndTestingRegion_Id(statusId, testingRegionId, pageRequest);
    }

    public CandidateEntity getById(String id) throws NoSuchElementException {
        return this.candidateRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Не найден кандидат с ID: " + id)
        );
    }

    @Transactional
    public void create(CandidateRequest candidateDto) throws NoSuchElementException {

        final NationalityEntity nationality = this.nationalityService.getById(candidateDto.getNationalityCode());
        final Set<LanguageEntity> languages = this.languageService.getAllSetByCodes(candidateDto.getLanguageCodes(), true);
        final Set<DriverLicenseEntity> driverLicenses = this.driverLicenseService.getAllSetByCodes(candidateDto.getDriverLicenseCodes(), true);
        final RecruitedMethodEntity recruitedMethod = this.recruitedMethodService.getById(candidateDto.getRecruitedMethodId());
        final UserEntity user = this.userService.createForCandidate(candidateDto.getUsername(), candidateDto.getPassword());
        final CandidateStatusEntity status = this.candidateStatusService.getById(1);
        final RegionEntity testingRegion = this.regionService.getById(candidateDto.getTestingRegionId());

        final CandidateEntity candidate = CandidateEntity.builder()
                .identificationNumber(candidateDto.getIdentificationNumber())
                .lastName(candidateDto.getLastName())
                .firstName(candidateDto.getFirstName())
                .middleName(candidateDto.getMiddleName())
                .birthDate(candidateDto.getBirthDate())
                .birthPlace(candidateDto.getBirthPlace())
                .testingRegion(testingRegion)
                .phoneNumber(candidateDto.getPhoneNumber())
                .nationality(nationality)
                .education(candidateDto.getEducation())
                .languages(languages)
                .driverLicenses(driverLicenses)
                .sport(candidateDto.getSport())
                .additionalData(candidateDto.getAdditionalData())
                .recruitedMethod(recruitedMethod)
                .recruitedMethodComment(candidateDto.getRecruitedMethodComment())
                .securityCheckResult(candidateDto.getSecurityCheckResult())
                .user(user)
                .status(status)
                .build();

        final CandidateEntity savedCandidate = this.candidateRepository.save(candidate);

        this.experienceService.createAll(savedCandidate, candidateDto.getExperiences());
    }

    public void reject(String iin) {
        final CandidateEntity candidate = this.candidateRepository.findById(iin)
                .orElseThrow(() -> new NoSuchElementException("Кандидат не найден"));
        final CandidateStatusEntity status = this.candidateStatusService.getById(5);
        candidate.setStatus(status);
        this.candidateRepository.save(candidate);
    }

    @Transactional
    public void sendToSecurityCheck(CandidateRequest candidateDto) throws NoSuchElementException {
        final NationalityEntity nationality = this.nationalityService.getById(candidateDto.getNationalityCode());
        final Set<LanguageEntity> languages = this.languageService.getAllSetByCodes(candidateDto.getLanguageCodes(), true);
        final Set<DriverLicenseEntity> driverLicenses = this.driverLicenseService.getAllSetByCodes(candidateDto.getDriverLicenseCodes(), true);
        final RecruitedMethodEntity recruitedMethod = this.recruitedMethodService.getById(candidateDto.getRecruitedMethodId());
        final CandidateStatusEntity status = this.candidateStatusService.getById(2);
        final RegionEntity testingRegion = this.regionService.getById(candidateDto.getTestingRegionId());

        final CandidateEntity candidate = this.candidateRepository.findById(candidateDto.getIdentificationNumber())
                .orElseThrow(() -> new NoSuchElementException("Кандидат не найден"));
        candidate.setLastName(candidateDto.getLastName());
        candidate.setFirstName(candidateDto.getFirstName());
        candidate.setMiddleName(candidateDto.getMiddleName());
        candidate.setBirthDate(candidateDto.getBirthDate());
        candidate.setBirthPlace(candidateDto.getBirthPlace());
        candidate.setTestingRegion(testingRegion);
        candidate.setPhoneNumber(candidateDto.getPhoneNumber());
        candidate.setNationality(nationality);
        candidate.setEducation(candidateDto.getEducation());
        candidate.setLanguages(languages);
        candidate.setDriverLicenses(driverLicenses);
        candidate.setSport(candidateDto.getSport());
        candidate.setAdditionalData(candidateDto.getAdditionalData());
        candidate.setRecruitedMethod(recruitedMethod);
        candidate.setRecruitedMethodComment(candidateDto.getRecruitedMethodComment());
        candidate.setSecurityCheckResult(candidateDto.getSecurityCheckResult());
        this.userService.updateUsername(candidateDto.getUsername(), candidate.getUser());
        candidate.setStatus(status);
        this.candidateRepository.save(candidate);

        this.experienceService.updateAll(candidate, candidateDto.getExperiences());
    }

    public void sendToApproval(CandidateRequest candidateDto) throws NoSuchElementException {
        final CandidateStatusEntity status = this.candidateStatusService.getById(3);
        final CandidateEntity candidate = this.candidateRepository.findById(candidateDto.getIdentificationNumber())
                .orElseThrow(() -> new NoSuchElementException("Кандидат не найден"));
        candidate.setSecurityCheckResult(candidateDto.getSecurityCheckResult());
        candidate.setStatus(status);
        this.candidateRepository.save(candidate);
    }

    public void approve(String iin, String areaOfActivity) throws NoSuchElementException {
        final CandidateStatusEntity status = this.candidateStatusService.getById(4);
        final CandidateEntity candidate = this.candidateRepository.findById(iin)
                .orElseThrow(() -> new NoSuchElementException("Кандидат не найден"));
        candidate.setStatus(status);
        candidate.setAreaOfActivity(areaOfActivity);
        this.candidateRepository.save(candidate);
    }

}
