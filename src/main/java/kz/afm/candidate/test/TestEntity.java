package kz.afm.candidate.test;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "test")
public class TestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String nameRus;

    @Column(nullable = false)
    private String nameKaz;

    @Column(nullable = false)
    private boolean isLimitless;

    private int duration;

    public TestEntity(String nameRus, String nameKaz, boolean isLimitless, int duration) {
        this.nameRus = nameRus;
        this.nameKaz = nameKaz;
        this.isLimitless = isLimitless;
        this.duration = duration;
    }

}
