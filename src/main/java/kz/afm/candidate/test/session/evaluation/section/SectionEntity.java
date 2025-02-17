package kz.afm.candidate.test.session.evaluation.section;

import jakarta.persistence.*;
import kz.afm.candidate.test.session.evaluation.scale.ScaleEntity;
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

    @Column(nullable = false, length = 50000)
    private String descriptionRus;

    @Column(nullable = false, length = 50000)
    private String descriptionKaz;

    private int lowerBound;

    private int upperBound;

    @ManyToOne
    @JoinColumn(name = "scale_id", nullable = false)
    private ScaleEntity scale;

    public SectionEntity(
            String descriptionRus,
            String descriptionKaz,
            int lowerBound,
            int upperBound,
            ScaleEntity scale
    ) {
        this.descriptionRus = descriptionRus;
        this.descriptionKaz = descriptionKaz;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.scale = scale;
    }

}
