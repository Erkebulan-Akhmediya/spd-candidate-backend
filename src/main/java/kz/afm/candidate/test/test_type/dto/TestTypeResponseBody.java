package kz.afm.candidate.test.test_type.dto;

import kz.afm.candidate.test.test_type.TestTypeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TestTypeResponseBody {
    private int id;
    private String nameRus;
    private String nameKaz;

    public static TestTypeResponseBody fromEntity(TestTypeEntity type) {
        return new TestTypeResponseBody(type.getId(), type.getNameRus(), type.getNameKaz());
    }

    public static List<TestTypeResponseBody> fromEntities(List<TestTypeEntity> types) {
        return types.stream().map(TestTypeResponseBody::fromEntity).toList();
    }

}
