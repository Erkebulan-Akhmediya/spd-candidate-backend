package kz.afm.candidate.test;

import kz.afm.candidate.dto.ResponseBodyWrapper;
import kz.afm.candidate.test.dto.CreateTestRequest;
import kz.afm.candidate.test.dto.GetAllTestsResponseBody;
import kz.afm.candidate.test.dto.TestResponse;
import kz.afm.candidate.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("test")
@RestController
public class TestController {

    private final TestService testService;

    @PostMapping
    public ResponseEntity<ResponseBodyWrapper<Void>> create(@RequestBody CreateTestRequest test) {
        try {
            this.testService.create(test);
            return ResponseEntity.ok(ResponseBodyWrapper.success());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        }
    }

    @GetMapping("all")
    public ResponseEntity<ResponseBodyWrapper<GetAllTestsResponseBody>> getAll(
            @AuthenticationPrincipal UserEntity requestingUser,
            @RequestParam(required = false, defaultValue = "-1") int pageSize,
            @RequestParam(required = false, defaultValue = "0") int pageNumber
    ) {
        try {
            final List<TestEntity> tests = this.testService.getAll(requestingUser, pageNumber, pageSize);
            final long allTestCount = this.testService.getAllCount();
            final List<TestResponse> testDtoList = tests.stream().map(TestResponse::new).toList();
            final GetAllTestsResponseBody responseBody = new GetAllTestsResponseBody(testDtoList, allTestCount);
            return ResponseEntity.ok(ResponseBodyWrapper.success(responseBody));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        }
    }

}
