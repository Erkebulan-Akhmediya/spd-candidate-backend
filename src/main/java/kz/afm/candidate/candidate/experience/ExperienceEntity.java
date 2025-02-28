package kz.afm.candidate.candidate.experience;

import jakarta.persistence.*;
import kz.afm.candidate.candidate.CandidateEntity;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "experience")
public class ExperienceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    @Column(nullable = false)
    public Date startDate;

    public Date endDate;

    @Column(nullable = false)
    public String position;

    @Column(nullable = false)
    public String companyName;

    @ManyToOne
    @JoinColumn(name = "candidate_identification_number", nullable = false)
    public CandidateEntity candidate;

    public ExperienceEntity(
            Date startDate,
            Date endDate,
            String position,
            String companyName,
            CandidateEntity candidate
    ) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.companyName = companyName;
        this.candidate = candidate;
    }

}
