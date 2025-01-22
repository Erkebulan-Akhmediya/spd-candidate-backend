package kz.afm.candidate.test;

import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityEntity;
import kz.afm.candidate.test.dto.CreateTestRequest;
import kz.afm.candidate.test.dto.GetAllTestsResponse;
import kz.afm.candidate.test.dto.TestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RequestMapping("test")
@RestController
public class TestController {

    private final TestService testService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> create(@ModelAttribute CreateTestRequest test) {
        try {
            this.testService.create(test);
            return ResponseEntity.ok("let him cook");
        } catch (NoSuchElementException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Ошибка сервера");
        }
    }

    @GetMapping("all")
    public ResponseEntity<GetAllTestsResponse> getAll(
            @RequestParam(required = false, defaultValue = "-1") int pageSize,
            @RequestParam(required = false, defaultValue = "0") int pageNumber
    ) {
        try {
            final List<TestResponse> tests = this.testService.getAll(pageNumber, pageSize)
                    .stream()
                    .map(
                            (TestEntity test) -> new TestResponse(
                                    test.getId(),
                                    test.getNameRus(),
                                    test.getNameKaz(),
                                    test.getAreaOfActivities().stream().map(AreaOfActivityEntity::getName).toList(),
                                    test.getDuration(),
                                    test.isLimitless()
                            )
                    )
                    .toList();
            return ResponseEntity.ok(new GetAllTestsResponse(null, tests, this.testService.getAllCount()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new GetAllTestsResponse("", null, 0));
        }
    }

}
