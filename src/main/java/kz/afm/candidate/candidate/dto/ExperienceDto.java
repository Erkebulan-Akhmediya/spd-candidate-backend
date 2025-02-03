package kz.afm.candidate.candidate.dto;

import kz.afm.candidate.candidate.experience.ExperienceEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExperienceDto {
    private Date startDate;
    private Date endDate;
    private String position;
    private String companyName;

    public static ExperienceDto fromEntity(ExperienceEntity experience) {
        return new ExperienceDto(
                experience.getStartDate(),
                experience.getEndDate(),
                experience.getPosition(),
                experience.getCompanyName()
        );
    }

    public static List<ExperienceDto> fromEntities(List<ExperienceEntity> experiences) {
        return experiences.stream().map(ExperienceDto::fromEntity).toList();
    }

}
