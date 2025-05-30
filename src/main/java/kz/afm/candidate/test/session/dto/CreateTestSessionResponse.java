package kz.afm.candidate.test.session.dto;

import kz.afm.candidate.test.TestEntity;
import kz.afm.candidate.test.dto.evaluation.ConditionalSectioningVariableDto;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class CreateTestSessionResponse {
    public long testSessionId;
    public Set<Long> questionIds;
    public int testTypeId;
    public int maxPointsPerQuestion;
    public boolean conditionallySectioned;
    public List<ConditionalSectioningVariableDto> conditionalVars;

    public CreateTestSessionResponse(
            TestEntity test,
            long testSessionId,
            Set<Long> questionIds,
            int maxPointsPerQuestion,
            List<ConditionalSectioningVariableDto> conditionalVars
    ) {
        this.testSessionId = testSessionId;
        this.questionIds = questionIds;
        this.testTypeId = test.getType().getId();
        this.maxPointsPerQuestion = maxPointsPerQuestion;
        this.conditionallySectioned = test.conditionallySectioned;
        this.conditionalVars = conditionalVars;
    }
}
