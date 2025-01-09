package kz.afm.candidate.reference.recruited_method;

import jakarta.persistence.*;
import kz.afm.candidate.candidate.CandidateEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recruited_method", schema = "reference")
public class RecruitedMethodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String nameRus;

    @Column(nullable = false)
    private String nameKaz;

    @OneToMany(mappedBy = "recruitedMethod")
    private Set<CandidateEntity> candidates;

}
