package kz.afm.candidate.test.test_type.point_distribution;

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
@Table(name = "point_distribution_test")
public class PointDistributionTestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "test_id",referencedColumnName = "id")
    private TestEntity test;

    private int maxPointsPerQuestion;

    public PointDistributionTestEntity(TestEntity test, int maxPointsPerQuestion) {
        this.test = test;
        this.maxPointsPerQuestion = maxPointsPerQuestion;
    }

}
