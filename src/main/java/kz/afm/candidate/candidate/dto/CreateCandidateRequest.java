package kz.afm.candidate.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class CreateCandidateRequest {
    private String identificationNumber;
    private String lastName;
    private String firstName;
    private String middleName;
    private Date birthDate;
    private String birthPlace;
    private String phoneNumber;
    private int nationalityCode;
    private String education;
    private Set<String> languageCodes;
    private Set<String> driverLicenseCodes;
    private String sport;
    private String additionalData;
    private int recruitedMethodId;
    private String recruitedMethodComment;
    private String securityCheckResult;
    private Set<ExperienceRequest> experiences;
    private String username;
    private String password;
}
