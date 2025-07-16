package kz.afm.candidate.test.session.evaluation.scale;

import jakarta.persistence.*;
import kz.afm.candidate.test.TestEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "test_scale")
public class ScaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(nullable = false, length = 1000)
    public String nameRus;

    @Column(nullable = false, length = 1000)
    public String nameKaz;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    public TestEntity test;

    public ScaleEntity(String nameRus, String nameKaz, TestEntity test) {
        this.nameRus = nameRus;
        this.nameKaz = nameKaz;
        this.test = test;
    }

}
