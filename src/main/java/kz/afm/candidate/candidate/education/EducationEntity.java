package kz.afm.candidate.candidate.education;

import jakarta.persistence.*;
import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.candidate.education.type.EducationTypeEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "education")
public class EducationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @ManyToOne
    @JoinColumn(name = "education_type_id", nullable = false)
    public EducationTypeEntity educationType;

    @Column(nullable = false)
    public Date startDate;

    public Date endDate;

    public String organization;

    public String major;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    public CandidateEntity candidate;

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
