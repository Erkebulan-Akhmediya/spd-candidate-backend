package kz.afm.candidate.test.session.evaluation.section.conditional.condition;

import jakarta.persistence.*;
import kz.afm.candidate.test.session.evaluation.section.SectionEntity;
import kz.afm.candidate.test.session.evaluation.section.conditional.variable.ConditionalSectioningVariableEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sectioning_condition")
public class SectioningConditionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @ManyToOne
    @JoinColumn(name = "variable_id", nullable = false)
    public ConditionalSectioningVariableEntity variable;

    @Column(nullable = false)
    public String operator;

    @Column(nullable = false)
    public List<String> value;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    public SectionEntity section;

    public SectioningConditionEntity(
            ConditionalSectioningVariableEntity variable,
            String operator,
            List<String> value,
            SectionEntity section
    ) {
        this.variable = variable;
        this.operator = operator;
        this.value = value;
        this.section = section;
    }

}
