package kz.afm.candidate.test;

import kz.afm.candidate.dto.ResponseBodyWrapper;
import kz.afm.candidate.test.dto.CreateTestRequest;
import kz.afm.candidate.test.dto.GetAllTestsResponseBody;
import kz.afm.candidate.test.dto.TestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

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
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

    @GetMapping("all")
    public ResponseEntity<ResponseBodyWrapper<GetAllTestsResponseBody>> getAll(
            @RequestParam(required = false, defaultValue = "-1") int pageSize,
            @RequestParam(required = false, defaultValue = "0") int pageNumber,
            @RequestParam(required = false, defaultValue = "any") String areaOfActivity
    ) {
        try {
            final List<TestEntity> entities = this.testService.getAll(pageNumber, pageSize, areaOfActivity);
            final long allTestCount = this.testService.getAllCount();
            final List<TestResponse> tests = TestResponse.fromEntities(entities);
            final GetAllTestsResponseBody responseBody = new GetAllTestsResponseBody(tests, allTestCount);
            return ResponseEntity.ok(ResponseBodyWrapper.success(responseBody));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

}
