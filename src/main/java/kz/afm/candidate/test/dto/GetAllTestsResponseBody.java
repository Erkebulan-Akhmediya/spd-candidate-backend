package kz.afm.candidate.test.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class GetAllTestsResponseBody {
    public List<TestResponse> tests;
    public long count;
}
