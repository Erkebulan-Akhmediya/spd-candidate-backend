package kz.afm.candidate.test.session.evaluation.section.conditional.variable;

import jakarta.persistence.*;
import kz.afm.candidate.test.TestEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "conditional_sectioning_variable")
public class ConditionalSectioningVariableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String name;

    public String type;

    public List<String> reference;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    public TestEntity test;

    public ConditionalSectioningVariableEntity(String name, String type, List<String> reference, TestEntity test) {
        this.name = name;
        this.type = type;
        this.reference = reference;
        this.test = test;
    }

}
