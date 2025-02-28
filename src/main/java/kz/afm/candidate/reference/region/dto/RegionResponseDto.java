package kz.afm.candidate.reference.region.dto;

import kz.afm.candidate.reference.region.RegionEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RegionResponseDto {
    public int id;
    public String nameRus;
    public String nameKaz;

    public RegionResponseDto(RegionEntity region) {
        this.id = region.id;
        this.nameRus = region.nameRus;
        this.nameKaz = region.nameKaz;
    }

}
