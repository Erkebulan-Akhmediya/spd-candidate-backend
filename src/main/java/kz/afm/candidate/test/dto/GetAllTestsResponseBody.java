package kz.afm.candidate.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetAllTestsResponseBody {
    private List<TestResponse> tests;
    private long count;
}
