package kz.afm.candidate.candidate.experience;

import jakarta.persistence.*;
import kz.afm.candidate.candidate.CandidateEntity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "experience")
public class ExperienceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Date startDate;

    private Date endDate;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String companyName;

    @ManyToOne
    @JoinColumn(name = "candidate_identification_number", nullable = false)
    private CandidateEntity candidate;

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
