package kz.afm.candidate.test.session.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateTestSessionResponse {
    private long testSessionId;
    private long questionCount;
}
