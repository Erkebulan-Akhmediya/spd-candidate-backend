package kz.afm.candidate.test.session.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestSessionAnswerRequest {
    private long questionId;
    private Object answer;
}
