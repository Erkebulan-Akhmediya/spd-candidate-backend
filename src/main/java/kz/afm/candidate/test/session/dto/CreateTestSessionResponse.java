package kz.afm.candidate.test.session.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class CreateTestSessionResponse {
    private long testSessionId;
    private Set<Long> questionIds;
    private int testType;
}
