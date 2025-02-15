package kz.afm.candidate.test.session.evaluation.increment;

import jakarta.persistence.*;
import kz.afm.candidate.test.session.evaluation.scale.ScaleEntity;
import kz.afm.candidate.test.option.OptionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "option_increment")
public class OptionIncrementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "option_id", nullable = false)
    private OptionEntity option;

    @ManyToOne
    @JoinColumn(name = "scale_id", nullable = false)
    private ScaleEntity scale;

    @Column(nullable = false)
    private int increment;

    public OptionIncrementEntity(OptionEntity option, ScaleEntity scale, int increment) {
        this.option = option;
        this.scale = scale;
        this.increment = increment;
    }

}
