package kz.afm.candidate.test.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class CreateVariantRequest {
    public List<CreateQuestionRequest> questions;
}
