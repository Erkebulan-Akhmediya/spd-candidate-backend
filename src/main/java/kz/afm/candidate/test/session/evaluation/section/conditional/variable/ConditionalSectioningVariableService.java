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

    private final Map<String, Integer> typeIndexes = new LinkedHashMap<>() {{
        put("number", 0);
        put("string", 1);
        put("boolean", 2);
        put("reference", 3);
    }};

    public int getTypeIndex(String typeName) {
        return this.typeIndexes.get(typeName);
    }

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
        this.getAllByTest(test).forEach((ConditionalSectioningVariableEntity var) -> vars.put(var.name, var));
        return vars;
    }

    public List<ConditionalSectioningVariableEntity> getAllByTest(TestEntity test) {
        return this.conditionalSectioningVariableRepository.findAllByTest(test);
    }

}
