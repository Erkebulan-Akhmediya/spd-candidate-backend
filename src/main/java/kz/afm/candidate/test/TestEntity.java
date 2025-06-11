package kz.afm.candidate.test;

import jakarta.persistence.*;

import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityEntity;
import kz.afm.candidate.test.test_type.TestTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

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

    @Column(nullable = false, length = 1000)
    private String nameRus;

    @Column(nullable = false, length = 1000)
    private String nameKaz;

    @Column(nullable = false, length = 10000)
    private String descriptionRus = "";

    @Column(nullable = false, length = 10000)
    private String descriptionKaz = "";

    @Column(nullable = false)
    private boolean isLimitless;

    private int duration;

    @ColumnDefault("false")
    @Column(nullable = false)
    public boolean conditionallySectioned;

    @ColumnDefault("false")
    @Column(nullable = false)
    public boolean deleted;

    @ManyToMany
    @JoinTable(
            name = "test_area_of_activity_rel",
            joinColumns = @JoinColumn(name = "test_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "area_of_activity_name", nullable = false)
    )
    public Set<AreaOfActivityEntity> areaOfActivities;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private TestTypeEntity type;

    public TestEntity(
            String nameRus,
            String nameKaz,
            String descriptionRus,
            String descriptionKaz,
            boolean isLimitless,
            int duration,
            Set<AreaOfActivityEntity> areaOfActivities,
            TestTypeEntity type,
            boolean conditionallySectioned
    ) {
        this.nameRus = nameRus;
        this.nameKaz = nameKaz;
        this.descriptionRus = descriptionRus;
        this.descriptionKaz = descriptionKaz;
        this.isLimitless = isLimitless;
        this.duration = duration;
        this.areaOfActivities = areaOfActivities;
        this.type = type;
        this.conditionallySectioned = conditionallySectioned;
    }

}
