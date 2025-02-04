package kz.afm.candidate.candidate.dto;

import kz.afm.candidate.candidate.education.EducationEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class EducationDto {
    private Date startDate;
    private Date endDate;
    private int type;
    private String organization;
    private String major;

    public static EducationDto fromEntity(EducationEntity education) {
        return new EducationDto(
                education.getStartDate(),
                education.getEndDate(),
                education.getEducationType().getId(),
                education.getOrganization(),
                education.getMajor()
        );
    }

    public static List<EducationDto> fromEntities(List<EducationEntity> educations) {
        return educations.stream().map(EducationDto::fromEntity).toList();
    }
}
