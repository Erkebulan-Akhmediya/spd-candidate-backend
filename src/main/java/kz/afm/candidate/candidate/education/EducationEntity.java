package kz.afm.candidate.candidate.education;

import jakarta.persistence.*;
import kz.afm.candidate.candidate.CandidateEntity;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "education_type_id", nullable = false)
    private EducationTypeEntity educationType;

    @Column(nullable = false)
    private Date startDate;

    private Date endDate;

    private String organization;

    private String major;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private CandidateEntity candidate;

    public EducationEntity(
            EducationTypeEntity type,
            Date startDate,
            Date endDate,
            String organization,
            String major,
            CandidateEntity candidate
    ) {
        this.educationType = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organization = organization;
        this.major = major;
        this.candidate = candidate;
    }

}
