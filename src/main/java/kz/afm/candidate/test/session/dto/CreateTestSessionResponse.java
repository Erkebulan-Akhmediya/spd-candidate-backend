package kz.afm.candidate.test.session.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class CreateTestSessionResponse {
    private String error;
    private long testSessionId;
    private Set<Long> questionIds;
}
