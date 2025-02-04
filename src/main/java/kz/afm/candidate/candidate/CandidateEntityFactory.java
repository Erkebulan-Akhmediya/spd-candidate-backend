package kz.afm.candidate.candidate;

import kz.afm.candidate.candidate.dto.CandidateRequest;
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
import org.springframework.stereotype.Component;

import java.util.Set;

@RequiredArgsConstructor
@Component
public class CandidateEntityFactory {
    private final NationalityService nationalityService;
    private final LanguageService languageService;
    private final DriverLicenseService driverLicenseService;
    private final RecruitedMethodService recruitedMethodService;
    private final RegionService regionService;
    private final UserService userService;

    private CandidateEntity candidate;

    public CandidateEntity createFrom(CandidateRequest candidateDto) {
        CandidateEntity candidate = new CandidateEntity();
        candidate.setIdentificationNumber(candidateDto.getIdentificationNumber());
        candidate = this.updateEntityUsingRequestDtoValues(candidate, candidateDto);

        final UserEntity user = this.userService.createForCandidate(
                candidateDto.getUsername(),
                candidateDto.getPassword()
        );
        candidate.setUser(user);

        return candidate;
    }

    public CandidateEntity updateEntityUsingRequestDtoValues(CandidateEntity candidate, CandidateRequest candidateDto) {
        this.candidate = candidate;
        this.setCandidateEntityFieldsUsingValuesFrom(candidateDto);
        this.setCandidateEntityFieldsUsingServicesAndValuesFrom(candidateDto);
        return this.candidate;
    }

    private void setCandidateEntityFieldsUsingValuesFrom(CandidateRequest candidateDto) {
        candidate.setLastName(candidateDto.getLastName());
        candidate.setFirstName(candidateDto.getFirstName());
        candidate.setMiddleName(candidateDto.getMiddleName());
        candidate.setBirthDate(candidateDto.getBirthDate());
        candidate.setBirthPlace(candidateDto.getBirthPlace());
        candidate.setPhoneNumber(candidateDto.getPhoneNumber());
        candidate.setSport(candidateDto.getSport());
        candidate.setAdditionalData(candidateDto.getAdditionalData());
        candidate.setRecruitedMethodComment(candidateDto.getRecruitedMethodComment());
        candidate.setSecurityCheckResult(candidateDto.getSecurityCheckResult());
    }

    private void setCandidateEntityFieldsUsingServicesAndValuesFrom(CandidateRequest candidateDto) {
        this.setNationality(candidateDto);
        this.setLanguages(candidateDto);
        this.setDriverLicenses(candidateDto);
        this.setRecruitedMethod(candidateDto);
        this.setTestingRegion(candidateDto);
    }

    private void setNationality(CandidateRequest candidateDto) {
        NationalityEntity nationality = this.nationalityService.getById(candidateDto.getNationalityCode());
        candidate.setNationality(nationality);
    }

    private void setLanguages(CandidateRequest candidateDto) {
        Set<String> languageCodes = candidateDto.getLanguageCodes();
        boolean notEmpty = true;
        Set<LanguageEntity> languages = this.languageService.getAllSetByCodes(languageCodes, notEmpty);
        candidate.setLanguages(languages);
    }

    private void setDriverLicenses(CandidateRequest candidateDto) {
        Set<String> driverLicenseCodes = candidateDto.getDriverLicenseCodes();
        boolean notEmpty = true;
        Set<DriverLicenseEntity> driverLicenses = this.driverLicenseService.getAllSetByCodes(driverLicenseCodes, notEmpty);
        candidate.setDriverLicenses(driverLicenses);
    }

    private void setRecruitedMethod(CandidateRequest candidateDto) {
        int recruitedMethodId = candidateDto.getRecruitedMethodId();
        RecruitedMethodEntity recruitedMethod = this.recruitedMethodService.getById(recruitedMethodId);
        candidate.setRecruitedMethod(recruitedMethod);
    }

    private void setTestingRegion(CandidateRequest candidateDto) {
        int testingRegionId = candidateDto.getTestingRegionId();
        RegionEntity testingRegion = this.regionService.getById(testingRegionId);
        candidate.setTestingRegion(testingRegion);
    }

}
