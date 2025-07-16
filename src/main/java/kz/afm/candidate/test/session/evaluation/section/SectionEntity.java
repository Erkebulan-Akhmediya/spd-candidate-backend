package kz.afm.candidate.test.session.evaluation.section;

import jakarta.persistence.*;
import kz.afm.candidate.test.session.evaluation.scale.ScaleEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "scale_section")
public class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(nullable = false, length = 50000)
    public String descriptionRus;

    @Column(nullable = false, length = 50000)
    public String descriptionKaz;

    public int lowerBound;

    public int upperBound;

    @ManyToOne
    @JoinColumn(name = "scale_id", nullable = false)
    public ScaleEntity scale;

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
