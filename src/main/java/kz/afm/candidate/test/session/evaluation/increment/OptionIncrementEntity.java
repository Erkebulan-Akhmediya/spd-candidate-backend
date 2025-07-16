package kz.afm.candidate.test.session.evaluation.increment;

import jakarta.persistence.*;
import kz.afm.candidate.test.session.evaluation.scale.ScaleEntity;
import kz.afm.candidate.test.option.OptionEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "option_increment")
public class OptionIncrementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @OneToOne
    @JoinColumn(name = "option_id", nullable = false)
    public OptionEntity option;

    @ManyToOne
    @JoinColumn(name = "scale_id", nullable = false)
    public ScaleEntity scale;

    @Column(nullable = false)
    public int increment;

    public OptionIncrementEntity(OptionEntity option, ScaleEntity scale, int increment) {
        this.option = option;
        this.scale = scale;
        this.increment = increment;
    }

}
