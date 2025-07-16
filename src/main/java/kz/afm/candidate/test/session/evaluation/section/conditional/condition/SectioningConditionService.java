package kz.afm.candidate.test.session.evaluation.section.conditional.condition;

import kz.afm.candidate.test.TestEntity;
import kz.afm.candidate.test.dto.evaluation.SectioningConditionDto;
import kz.afm.candidate.test.session.evaluation.section.SectionEntity;
import kz.afm.candidate.test.session.evaluation.section.conditional.variable.ConditionalSectioningVariableEntity;
import kz.afm.candidate.test.session.evaluation.section.conditional.variable.ConditionalSectioningVariableService;
import kz.afm.candidate.test.session.evaluation.section.conditional.variable_value.ConditionalSectioningVariableValueEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class SectioningConditionService {

    private final ConditionalSectioningVariableService conditionalSectioningVariableService;

    private final SectioningConditionRepository sectioningConditionRepository;

    public void create(SectionEntity section, List<SectioningConditionDto> conditionDtos) {
        final TestEntity test = section.scale.test;
        final Map<String, ConditionalSectioningVariableEntity> vars = this.conditionalSectioningVariableService.getAllByTestAsMap(test);
        final List<SectioningConditionEntity> conditions = conditionDtos.parallelStream()
                .map(
                        (SectioningConditionDto conditionDto) -> new SectioningConditionEntity(
                                vars.get(conditionDto.varName),
                                conditionDto.operator,
                                conditionDto.value,
                                section
                        )
                )
                .toList();
        this.sectioningConditionRepository.saveAll(conditions);
    }

    public List<SectioningConditionEntity> getAllBySection(SectionEntity section) {
        return this.sectioningConditionRepository.findAllBySection(section);
    }

    public boolean isTrue(
            SectioningConditionEntity condition,
            Map<Long, ConditionalSectioningVariableValueEntity> varIdsAndValues
    ) {
        final ConditionalSectioningVariableEntity var = condition.variable;
        final List<String> lhs = varIdsAndValues.get(var.id).value;
        final String operator = condition.operator;
        final List<String> rhs = condition.value;
        if (Objects.equals(var.type, "reference")) {
            return this.isExpressionTrue(lhs, operator, rhs);
        } else {
            return this.isExpressionTrue(lhs.getFirst(), operator, rhs.getFirst());
        }
    }

    private boolean isExpressionTrue(String lhs, String operator, String rhs) {
        if (Objects.equals(operator, "equals")) return lhs.equals(rhs);
        if (Objects.equals(operator, "not equals")) return !lhs.equals(rhs);
        final int intLhs = Integer.parseInt(lhs);
        final int intRhs = Integer.parseInt(rhs);
        if (Objects.equals(operator, "greater")) return intLhs > intRhs;
        if (Objects.equals(operator, "greater or equal")) return intLhs >= intRhs;
        if (Objects.equals(operator, "less")) return intLhs < intRhs;
        if (Objects.equals(operator, "less or equal")) return intLhs <= intRhs;
        throw new UnsupportedOperationException("оператор не допустим к не справочным типам данных");
    }

    private boolean isExpressionTrue(List<String> lhs, String operator, List<String> rhs) {
        final Set<String> setRhs = new HashSet<>(rhs);
        for (String v : lhs) {
            if (
                    (Objects.equals(operator, "not in") && setRhs.contains(v)) ||
                            (Objects.equals(operator, "in") && !setRhs.contains(v))
            ) return false;
        }
        return true;
    }

}
