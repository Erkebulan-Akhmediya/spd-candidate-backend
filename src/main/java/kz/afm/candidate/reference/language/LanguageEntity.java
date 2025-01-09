package kz.afm.candidate.reference.language;

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
@Table(name = "language", schema = "reference")
public class LanguageEntity {

    @Id
    private String code;

    @Column(name = "name_rus", nullable = false)
    private String nameRus;

    @Column(name = "name_kaz", nullable = false)
    private String nameKaz;

    @ManyToMany(mappedBy = "languages")
    private Set<CandidateEntity> candidates;

}
