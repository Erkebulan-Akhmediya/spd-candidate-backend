package kz.afm.candidate.test.evaluation.scale;

import jakarta.persistence.*;
import kz.afm.candidate.test.TestEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "test_scale")
public class ScaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private String nameRus;

    @Column(nullable = false)
    private String nameKaz;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private TestEntity test;

    public ScaleEntity(String nameRus, String nameKaz, TestEntity test) {
        this.nameRus = nameRus;
        this.nameKaz = nameKaz;
        this.test = test;
    }

}
