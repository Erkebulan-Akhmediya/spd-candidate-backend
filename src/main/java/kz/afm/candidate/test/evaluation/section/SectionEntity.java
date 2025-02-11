package kz.afm.candidate.test.evaluation.section;

import jakarta.persistence.*;
import kz.afm.candidate.test.evaluation.scale.ScaleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "scale_section")
public class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String descriptionRus;

    @Column(nullable = false)
    private String descriptionKaz;

    private int lowerBound;

    private int upperBound;

    @ManyToOne
    @JoinColumn(name = "scale_id", nullable = false)
    private ScaleEntity scale;

}
