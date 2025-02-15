package kz.afm.candidate.test.session.evaluation.result;

import jakarta.persistence.*;
import kz.afm.candidate.test.session.evaluation.scale.ScaleEntity;
import kz.afm.candidate.test.session.TestSessionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "result")
public class ResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "test_session_id", nullable = false)
    private TestSessionEntity testSession;

    @ManyToOne
    @JoinColumn(name = "scale_id", nullable = false)
    private ScaleEntity scale;

    private int score;

    public ResultEntity(TestSessionEntity testSession, ScaleEntity scale) {
        this.testSession = testSession;
        this.scale = scale;
        this.score = 0;
    }

}
