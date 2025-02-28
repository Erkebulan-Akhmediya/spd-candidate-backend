package kz.afm.candidate.candidate.dto;

import kz.afm.candidate.candidate.experience.ExperienceEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class ExperienceDto {
    public Date startDate;
    public boolean untilNow;
    public Date endDate;
    public String position;
    public String companyName;

    public ExperienceDto(ExperienceEntity experience) {
        this.startDate = experience.startDate;
        this.untilNow = experience.endDate == null;
        this.endDate = experience.endDate;
        this.position = experience.position;
        this.companyName = experience.companyName;
    }

}
