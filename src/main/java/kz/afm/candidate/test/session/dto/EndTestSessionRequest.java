package kz.afm.candidate.test.session.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class EndTestSessionRequest {

    public List<TestSessionAnswerRequest> answers;

    @JsonProperty("conditionalVarVals")
    public List<ConditionalSectioningVariableValueDto> conditionalSectioningVariableValues;

}
