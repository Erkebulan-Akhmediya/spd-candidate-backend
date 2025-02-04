package kz.afm.candidate.candidate.dto.get_by_id;

import kz.afm.candidate.candidate.dto.EducationDto;
import kz.afm.candidate.candidate.dto.ExperienceDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Builder
public class CandidateResponseBody {
    private String lastName;
    private String firstName;
    private String middleName;
    private Date birthDate;
    private String birthPlace;
    private int testingRegionId;
    private String identificationNumber;
    private String phoneNumber;
    private int nationalityCode;
    @Setter private List<String> languageCodes;
    @Setter private List<String> driverLicenseCodes;
    @Setter private List<EducationDto> education;
    private String sport;
    private int recruitedMethodId;
    private String recruitedMethodComment;
    @Setter private List<ExperienceDto> experiences;
    private String securityCheckResult;
    private String additionalData;
    private String username;
    @Setter private String areaOfActivity;
}
