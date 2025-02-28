package kz.afm.candidate.candidate.dto;

import kz.afm.candidate.candidate.education.EducationEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
public class EducationDto {
    public Date startDate;
    public Date endDate;
    public boolean untilNow;
    public int type;
    public String organization;
    public String major;

    public EducationDto(EducationEntity education) {
        this.startDate = education.startDate;
        this.endDate = education.endDate;
        this.untilNow = education.endDate == null;
        this.type = education.educationType.getId();
        this.organization = education.organization;
        this.major = education.major;
    }

}
