package kz.afm.candidate.reference.recruited_method.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetAllRecruitedMethodResponse {
    private String error;
    private List<RecruitedMethodResponse> recruitedMethods;
}
