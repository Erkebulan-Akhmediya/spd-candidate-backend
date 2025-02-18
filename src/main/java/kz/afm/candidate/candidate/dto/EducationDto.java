package kz.afm.candidate.candidate.dto;

import kz.afm.candidate.candidate.education.EducationEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EducationDto {
    public Date startDate;
    public Date endDate;
    public int type;
    public String organization;
    public String major;

    public EducationDto(EducationEntity education) {
        this.startDate = education.getStartDate();
        this.endDate = education.getEndDate();
        this.type = education.getEducationType().getId();
        this.organization = education.getOrganization();
        this.major = education.getMajor();
    }

}
