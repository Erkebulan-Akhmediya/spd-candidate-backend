package kz.afm.candidate.test.session.evaluation.section.conditional.variable;

import kz.afm.candidate.test.TestEntity;
import kz.afm.candidate.test.dto.evaluation.ConditionalSectioningVariableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ConditionalSectioningVariableService {

    private final ConditionalSectioningVariableRepository conditionalSectioningVariableRepository;

    private final Map<Integer, String> typeNames = new LinkedHashMap<>() {{
        put(0, "number");
        put(1, "string");
        put(2, "boolean");
        put(3, "reference");
    }};

    public void create(TestEntity test, List<ConditionalSectioningVariableDto> varDtos) {
        final List<ConditionalSectioningVariableEntity> vars = varDtos.stream()
                .map(
                        (ConditionalSectioningVariableDto var) -> new ConditionalSectioningVariableEntity(
                                var.name,
                                this.typeNames.get(var.type),
                                var.reference,
                                test
                        )
                )
                .toList();
        this.conditionalSectioningVariableRepository.saveAll(vars);
    }

    public Map<String, ConditionalSectioningVariableEntity> getAllByTestAsMap(TestEntity test) {
        final Map<String, ConditionalSectioningVariableEntity> vars = new LinkedHashMap<>();
        this.conditionalSectioningVariableRepository.findAllByTest(test)
                .forEach((ConditionalSectioningVariableEntity var) -> vars.put(var.name, var));
        return vars;
    }

}
