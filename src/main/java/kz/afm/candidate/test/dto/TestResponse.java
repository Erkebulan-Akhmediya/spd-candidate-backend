package kz.afm.candidate.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TestResponse {
    private long id;
    private String nameRus;
    private String nameKaz;
}
