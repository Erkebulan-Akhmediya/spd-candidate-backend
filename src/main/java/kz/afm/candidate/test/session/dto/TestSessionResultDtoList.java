package kz.afm.candidate.test.session.dto;

import java.util.List;

public class TestSessionResultDtoList {
    public String resultType;
    public List<TestSessionResultDto> results;

    public TestSessionResultDtoList(String resultType, List<TestSessionResultDto> results) {
        this.resultType = resultType;
        this.results = results;
    }
}
