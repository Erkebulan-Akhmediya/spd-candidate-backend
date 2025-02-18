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
    public long id;
    public String nameRus;
    public String nameKaz;
    public List<String> areasOfActivities;
    public int duration;
    @JsonProperty("isLimitless")
    public boolean limitless;

    public TestResponse(TestEntity test) {
        this.id = test.getId();
        this.nameRus = test.getNameRus();
        this.nameKaz = test.getNameKaz();
        this.areasOfActivities = test.getAreaOfActivities().stream().map(AreaOfActivityEntity::getName).toList();
        this.duration = test.getDuration();
        this.limitless = test.isLimitless();
    }

    public static List<TestResponse> fromEntities(List<TestEntity> tests) {
        return tests.stream().map(TestResponse::new).toList();
    }

}
