package kz.afm.candidate.test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TestResponse {
    private long id;
    private String nameRus;
    private String nameKaz;
    private List<String> areasOfActivities;
    private int duration;
    @JsonProperty("isLimitless")
    private boolean isLimitless;
}
