package kz.afm.candidate.reference.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegionResponse {
    private int id;
    private String nameRus;
    private String nameKaz;
}
