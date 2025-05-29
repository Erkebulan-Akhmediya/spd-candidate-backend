package kz.afm.candidate.test.session.evaluation.section.conditional.condition;

import kz.afm.candidate.test.TestEntity;
import kz.afm.candidate.test.dto.evaluation.SectioningConditionDto;
import kz.afm.candidate.test.session.evaluation.section.SectionEntity;
import kz.afm.candidate.test.session.evaluation.section.conditional.variable.ConditionalSectioningVariableEntity;
import kz.afm.candidate.test.session.evaluation.section.conditional.variable.ConditionalSectioningVariableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SectioningConditionService {

    private final ConditionalSectioningVariableService conditionalSectioningVariableService;

    private final SectioningConditionRepository sectioningConditionRepository;

    public void create(SectionEntity section, List<SectioningConditionDto> conditionDtos) {
        final TestEntity test = section.getScale().getTest();
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

}
