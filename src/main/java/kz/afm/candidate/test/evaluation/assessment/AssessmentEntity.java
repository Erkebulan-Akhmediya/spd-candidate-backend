package kz.afm.candidate.test.evaluation.assessment;

import jakarta.persistence.*;
import kz.afm.candidate.test.session.answer.TestSessionAnswerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "assessment")
public class AssessmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    @JoinColumn(name = "test_session_answer_id", nullable = false)
    private TestSessionAnswerEntity testSessionAnswer;

    private String result;

}
