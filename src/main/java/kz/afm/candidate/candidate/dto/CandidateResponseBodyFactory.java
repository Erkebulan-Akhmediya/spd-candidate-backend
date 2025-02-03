package kz.afm.candidate.candidate.dto;

import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityEntity;
import kz.afm.candidate.candidate.experience.ExperienceEntity;
import kz.afm.candidate.candidate.experience.ExperienceService;
import kz.afm.candidate.reference.driver_license.DriverLicenseEntity;
import kz.afm.candidate.reference.driver_license.DriverLicenseService;
import kz.afm.candidate.reference.language.LanguageEntity;
import kz.afm.candidate.reference.language.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class CandidateResponseBodyFactory {
    private final LanguageService languageService;
    private final DriverLicenseService driverLicenseService;
    private final ExperienceService experienceService;

    public CandidateResponseBody createFrom(CandidateEntity candidate) {

        final List<String> languageCodes = this.getLanguageCodes(candidate);
        final List<String> driverLicenseCodes = getDriverLicenseCodes(candidate);
        final List<ExperienceDto> experiences = this.getExperiences(candidate);
        final String areaOfActivityName = this.getAreaOfActivityName(candidate);

        final CandidateResponseBody response = this.build(candidate);

        response.setLanguageCodes(languageCodes);
        response.setDriverLicenseCodes(driverLicenseCodes);
        response.setExperiences(experiences);
        response.setAreaOfActivity(areaOfActivityName);

        return response;
    }

    private List<String> getLanguageCodes(CandidateEntity candidate) {
        final Set<LanguageEntity> languages = candidate.getLanguages();
        return this.languageService.extractCodes(languages);
    }

    private List<String> getDriverLicenseCodes(CandidateEntity candidate) {
        final Set<DriverLicenseEntity> driverLicenses = candidate.getDriverLicenses();
        return this.driverLicenseService.extractCodes(driverLicenses);
    }

    private List<ExperienceDto> getExperiences(CandidateEntity candidate) {
        final String identificationNumber = candidate.getIdentificationNumber();
        final List<ExperienceEntity> experiences = this.experienceService.getByCandidate(identificationNumber);
        return ExperienceDto.fromEntities(experiences);
    }

    private String getAreaOfActivityName(CandidateEntity candidate) {
        final AreaOfActivityEntity areaOfActivity = candidate.getAreaOfActivity();
        final boolean isAreaOfActivityPresent = areaOfActivity != null;
        if (isAreaOfActivityPresent) {
            return areaOfActivity.getName();
        }
        return null;
    }

    private CandidateResponseBody build(CandidateEntity candidate) {
        return CandidateResponseBody.builder()
                .lastName(candidate.getLastName())
                .firstName(candidate.getFirstName())
                .middleName(candidate.getMiddleName())
                .birthDate(candidate.getBirthDate())
                .birthPlace(candidate.getBirthPlace())
                .testingRegionId(candidate.getTestingRegion().getId())
                .identificationNumber(candidate.getIdentificationNumber())
                .phoneNumber(candidate.getPhoneNumber())
                .nationalityCode(candidate.getNationality().getCode())
                .education(candidate.getEducation())
                .sport(candidate.getSport())
                .recruitedMethodId(candidate.getRecruitedMethod().getId())
                .recruitedMethodComment(candidate.getRecruitedMethodComment())
                .securityCheckResult(candidate.getSecurityCheckResult())
                .additionalData(candidate.getAdditionalData())
                .username(candidate.getUser().getUsername())
                .build();
    }

}
