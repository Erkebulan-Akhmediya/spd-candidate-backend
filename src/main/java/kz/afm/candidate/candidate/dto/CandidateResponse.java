package kz.afm.candidate.candidate.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Builder
public class CandidateResponse {
    private String error;

    private String lastName;
    private String firstName;
    private String middleName;
    private Date birthDate;
    private String birthPlace;
    private int testingRegionId;
    private String identificationNumber;
    private String phoneNumber;
    private int nationalityCode;
    private List<String> languageCodes;
    private List<String> driverLicenseCodes;
    private String education;
    private String sport;
    private int recruitedMethodId;
    private String recruitedMethodComment;
    private List<ExperienceDto> experiences;
    private String securityCheckResult;
    private String additionalData;
    private String username;

    @Setter
    private String areaOfActivity;
}
