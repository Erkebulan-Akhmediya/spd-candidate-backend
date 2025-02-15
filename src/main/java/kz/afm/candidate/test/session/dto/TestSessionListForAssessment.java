package kz.afm.candidate.test.session.dto;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TestSessionListForAssessment {
    public long count;
    public List<TestSessionDto> testSessions;
}
