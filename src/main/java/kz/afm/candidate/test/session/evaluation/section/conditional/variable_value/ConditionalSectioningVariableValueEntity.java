package kz.afm.candidate.test.session.evaluation.section.conditional.variable_value;

import jakarta.persistence.*;
import kz.afm.candidate.test.session.TestSessionEntity;
import kz.afm.candidate.test.session.evaluation.section.conditional.variable.ConditionalSectioningVariableEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "conditional_sectioning_variable_value")
public class ConditionalSectioningVariableValueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @ManyToOne
    @JoinColumn(name = "variable_id", nullable = false)
    public ConditionalSectioningVariableEntity variable;

    @Column(nullable = false)
    public List<String> value;

    @ManyToOne
    @JoinColumn(name = "test_session_id", nullable = false)
    public TestSessionEntity testSession;

    public ConditionalSectioningVariableValueEntity(
            ConditionalSectioningVariableEntity variable,
            List<String> value,
            TestSessionEntity testSession
    ) {
        this.variable = variable;
        this.value = value;
        this.testSession = testSession;
    }

}
