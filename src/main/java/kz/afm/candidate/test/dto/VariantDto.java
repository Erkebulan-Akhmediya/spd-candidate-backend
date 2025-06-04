package kz.afm.candidate.test.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class VariantDto {
    public List<QuestionDto> questions;
}
