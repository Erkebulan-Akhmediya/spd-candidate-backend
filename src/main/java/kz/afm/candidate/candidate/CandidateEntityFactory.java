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

    public CandidateEntity createFrom(CandidateRequest candidateDto) {
        CandidateEntity candidate = this.updateEntityUsingRequestDtoValues(new CandidateEntity(), candidateDto);
        UserEntity user = this.userService.createForCandidate(candidateDto.username, candidateDto.password);
        candidate.setUser(user);
        return candidate;
    }

    public CandidateEntity updateEntityUsingRequestDtoValues(CandidateEntity candidate, CandidateRequest candidateDto) {
        NationalityEntity nationality = this.nationalityService.getUsing(candidateDto);
        Set<LanguageEntity> languages = this.languageService.getSetOfAllUsing(candidateDto);
        Set<DriverLicenseEntity> driverLicenses = this.driverLicenseService.getSetOfAllUsing(candidateDto);
        RecruitedMethodEntity recruitedMethod = this.recruitedMethodService.getUsing(candidateDto);
        RegionEntity testingRegion = this.regionService.getUsing(candidateDto);

        candidate.setIdentificationNumber(candidateDto.identificationNumber);
        candidate.setLastName(candidateDto.lastName);
        candidate.setFirstName(candidateDto.firstName);
        candidate.setMiddleName(candidateDto.middleName);
        candidate.setBirthDate(candidateDto.birthDate);
        candidate.setBirthPlace(candidateDto.birthPlace);
        candidate.setPhoneNumber(candidateDto.phoneNumber);
        candidate.setSport(candidateDto.sport);
        candidate.setAdditionalData(candidateDto.additionalData);
        candidate.setRecruitedMethodComment(candidateDto.recruitedMethodComment);
        candidate.setSecurityCheckResult(candidateDto.securityCheckResult);
        candidate.setPhotoFileName(candidateDto.photoFileName);
        candidate.setNationality(nationality);
        candidate.setLanguages(languages);
        candidate.setDriverLicenses(driverLicenses);
        candidate.setRecruitedMethod(recruitedMethod);
        candidate.setTestingRegion(testingRegion);
        return candidate;
    }

}
