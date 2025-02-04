package kz.afm.candidate.candidate.education;

import jakarta.persistence.*;
import kz.afm.candidate.candidate.education.type.EducationTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "education")
public class EducationEntity {

    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "education_type_id", nullable = false)
    private EducationTypeEntity educationType;

    private Date startDate;

    private Date endDate;

    private String organization;

    private String major;

}
