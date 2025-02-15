package kz.afm.candidate.test.session.dto;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class TestSessionListForAssessmentResponse {
    public long count;
    public List<TestSessionForAssessment> testSessions;
}
