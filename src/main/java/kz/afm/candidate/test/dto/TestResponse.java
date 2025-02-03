package kz.afm.candidate.test.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityEntity;
import kz.afm.candidate.test.TestEntity;
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
    private boolean limitless;

    public static TestResponse fromEntity(TestEntity test) {
        return new TestResponse(
                test.getId(),
                test.getNameRus(),
                test.getNameKaz(),
                test.getAreaOfActivities().stream().map(AreaOfActivityEntity::getName).toList(),
                test.getDuration(),
                test.isLimitless()
        );
    }

    public static List<TestResponse> fromEntities(List<TestEntity> tests) {
        return tests.stream().map(TestResponse::fromEntity).toList();
    }

}
