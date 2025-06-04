package kz.afm.candidate.test.dto.evaluation;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ScaleDto {
    public int index;
    public String nameRus;
    public String nameKaz;
    public List<ScaleSectionDto> sections;
}
