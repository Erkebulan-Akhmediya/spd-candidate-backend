package kz.afm.candidate.test.session.evaluation.section.conditional.variable_value;

import kz.afm.candidate.test.session.TestSessionEntity;
import kz.afm.candidate.test.session.dto.ConditionalSectioningVariableValueDto;
import kz.afm.candidate.test.session.evaluation.section.conditional.variable.ConditionalSectioningVariableEntity;
import kz.afm.candidate.test.session.evaluation.section.conditional.variable.ConditionalSectioningVariableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ConditionalSectioningVariableValueService {

    private final ConditionalSectioningVariableService conditionalSectioningVariableService;

    private final ConditionalSectioningVariableValueRepository conditionalSectioningVariableValueRepository;

    public void create(TestSessionEntity testSession, List<ConditionalSectioningVariableValueDto> varValueDtos) {
        final List<ConditionalSectioningVariableValueEntity> values = new LinkedList<>();
        final Map<String, ConditionalSectioningVariableEntity> vars = this.conditionalSectioningVariableService
                .getAllByTestAsMap(testSession.getVariant().getTest());
        for (ConditionalSectioningVariableValueDto varValueDto : varValueDtos) {
            final ConditionalSectioningVariableEntity var = vars.get(varValueDto.name);
            final ConditionalSectioningVariableValueEntity value = new ConditionalSectioningVariableValueEntity(
                    var,
                    varValueDto.value,
                    testSession
            );
            values.add(value);
        }
        this.conditionalSectioningVariableValueRepository.saveAll(values);
    }

    public Map<Long, ConditionalSectioningVariableValueEntity> getAllByTestSessionAsMap(TestSessionEntity testSession) {
        final Map<Long, ConditionalSectioningVariableValueEntity> varsAndValues = new LinkedHashMap<>();
        final List<ConditionalSectioningVariableValueEntity> values = this.getAllByTestSession(testSession);
        for (ConditionalSectioningVariableValueEntity value : values) {
            varsAndValues.put(value.variable.id, value);
        }
        return varsAndValues;
    }

    public List<ConditionalSectioningVariableValueEntity> getAllByTestSession(TestSessionEntity testSession) {
        return this.conditionalSectioningVariableValueRepository.findAllByTestSession(testSession);
    }

}
