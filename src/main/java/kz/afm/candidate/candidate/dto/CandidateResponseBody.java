package kz.afm.candidate.candidate.dto;

import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityEntity;
import kz.afm.candidate.candidate.education.EducationEntity;
import kz.afm.candidate.candidate.experience.ExperienceEntity;
import kz.afm.candidate.reference.driver_license.DriverLicenseEntity;
import kz.afm.candidate.reference.language.LanguageEntity;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class CandidateResponseBody {
    public String lastName;
    public String firstName;
    public String middleName;
    public Date birthDate;
    public String birthPlace;
    public int testingRegionId;
    public String identificationNumber;
    public String phoneNumber;
    public int nationalityCode;
    public List<String> languageCodes;
    public List<String> driverLicenseCodes;
    public List<EducationDto> education;
    public String sport;
    public int recruitedMethodId;
    public String recruitedMethodComment;
    public List<ExperienceDto> experiences;
    public String securityCheckResult;
    public String additionalData;
    public String username;
    public String areaOfActivity;
    public String photoFileName;

    public CandidateResponseBody(CandidateEntity candidate, List<ExperienceEntity> experiences, List<EducationEntity> education) {
        this.lastName = candidate.getLastName();
        this.firstName = candidate.getFirstName();
        this.middleName = candidate.getMiddleName();
        this.birthDate = candidate.getBirthDate();
        this.birthPlace = candidate.getBirthPlace();
        this.testingRegionId = candidate.getTestingRegion().getId();
        this.identificationNumber = candidate.getIdentificationNumber();
        this.phoneNumber = candidate.getPhoneNumber();
        this.nationalityCode = candidate.getNationality().getCode();

        Set<LanguageEntity> languages = candidate.getLanguages();
        this.languageCodes = languages.stream().map(LanguageEntity::getCode).toList();

        Set<DriverLicenseEntity> driverLicenses = candidate.getDriverLicenses();
        this.driverLicenseCodes = driverLicenses.stream().map(DriverLicenseEntity::getCode).toList();

        this.education = education.stream().map(EducationDto::new).toList();

        this.sport = candidate.getSport();
        this.recruitedMethodId = candidate.getRecruitedMethod().getId();
        this.recruitedMethodComment = candidate.getRecruitedMethodComment();

        this.experiences = experiences.stream().map(ExperienceDto::new).toList();

        this.securityCheckResult = candidate.getSecurityCheckResult();
        this.additionalData = candidate.getAdditionalData();
        this.username = candidate.getUser().getUsername();

        AreaOfActivityEntity areaOfActivity = candidate.getAreaOfActivity();
        this.areaOfActivity = areaOfActivity == null ? null : areaOfActivity.getName();

        this.photoFileName = candidate.getPhotoFileName();
    }

}
