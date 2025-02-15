package kz.afm.candidate.test.session.answer;

import jakarta.persistence.*;
import kz.afm.candidate.test.option.OptionEntity;
import kz.afm.candidate.test.question.QuestionEntity;
import kz.afm.candidate.test.session.TestSessionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "test_session_answer")
public class TestSessionAnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "test_session_id", nullable = false)
    private TestSessionEntity testSession;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private OptionEntity option;

    private String answer;

    public TestSessionAnswerEntity(TestSessionEntity testSession, QuestionEntity question, String answer) {
        this.testSession = testSession;
        this.question = question;
        this.answer = answer;
    }

    public TestSessionAnswerEntity(TestSessionEntity testSession, QuestionEntity question, OptionEntity option) {
        this.testSession = testSession;
        this.question = question;
        this.option = option;
    }

    public TestSessionAnswerEntity(TestSessionEntity testSession, QuestionEntity question, OptionEntity option, String answer) {
        this.testSession = testSession;
        this.question = question;
        this.option = option;
        this.answer = answer;
    }

}
