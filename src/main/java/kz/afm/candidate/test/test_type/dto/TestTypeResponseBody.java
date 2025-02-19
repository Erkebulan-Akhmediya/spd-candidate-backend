package kz.afm.candidate.test.test_type.dto;

import kz.afm.candidate.test.test_type.TestTypeEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class TestTypeResponseBody {
    public int id;
    public String nameRus;
    public String nameKaz;
    public boolean automaticallyEvaluated;

    public TestTypeResponseBody(TestTypeEntity type) {
        this.id = type.getId();
        this.nameRus = type.getNameRus();
        this.nameKaz = type.getNameKaz();
        this.automaticallyEvaluated = type.isAutomaticallyEvaluated();
    }

}
