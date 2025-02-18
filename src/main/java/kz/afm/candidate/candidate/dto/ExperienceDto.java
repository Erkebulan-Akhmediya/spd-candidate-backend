package kz.afm.candidate.candidate.dto;

import kz.afm.candidate.candidate.experience.ExperienceEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class ExperienceDto {
    public Date startDate;
    public Date endDate;
    public String position;
    public String companyName;

    public ExperienceDto(ExperienceEntity experience) {
        this.startDate = experience.getStartDate();
        this.endDate = experience.getEndDate();
        this.position = experience.getPosition();
        this.companyName = experience.getCompanyName();
    }

}
