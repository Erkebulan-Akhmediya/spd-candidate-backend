package kz.afm.candidate.reference.region.dto;

import kz.afm.candidate.reference.region.RegionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RegionResponse {
    private int id;
    private String nameRus;
    private String nameKaz;

    public static RegionResponse fromEntity(RegionEntity region) {
        return new RegionResponse(region.getId(), region.getNameRus(), region.getNameKaz());
    }

    public static List<RegionResponse> fromEntities(List<RegionEntity> regions) {
        return regions.stream().map(RegionResponse::fromEntity).toList();
    }
}
