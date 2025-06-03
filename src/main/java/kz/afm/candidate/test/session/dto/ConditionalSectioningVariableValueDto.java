package kz.afm.candidate.test.session.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class ConditionalSectioningVariableValueDto {

    @JsonProperty("varName")
    public String name;

    public List<String> value;

}
