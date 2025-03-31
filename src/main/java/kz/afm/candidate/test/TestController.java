package kz.afm.candidate.test;

import kz.afm.candidate.dto.ResponseBodyWrapper;
import kz.afm.candidate.test.dto.*;
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

    @GetMapping("/essay/topic/all")
    public ResponseEntity<ResponseBodyWrapper<List<EssayTopicResponse>>> getEssayTopics() {
        try {
            final List<EssayTopicResponse> topics = this.testService.getEssayTopics()
                    .stream()
                    .map(EssayTopicResponse::new)
                    .toList();
            return ResponseEntity.ok(ResponseBodyWrapper.success(topics));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        }
    }

    @PostMapping("/essay/topic")
    public ResponseEntity<ResponseBodyWrapper<Void>> createEssayTopics(@RequestBody EssayTopicRequest topic) {
        try {
            this.testService.createEssayTopic(topic.nameRus, topic.nameKaz);
            return ResponseEntity.ok(ResponseBodyWrapper.success());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        }
    }

    @PutMapping("/essay/topic/{variantId}")
    public ResponseEntity<ResponseBodyWrapper<Void>> updateEssayTopic(
            @PathVariable("variantId") long variantId,
            @RequestBody EssayTopicRequest topic
    ) {
        try {
            this.testService.updateEssayTopicByVariantId(variantId, topic.nameRus, topic.nameKaz);
            return ResponseEntity.ok(ResponseBodyWrapper.success());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        }
    }

    @DeleteMapping("/essay/topic/{variantId}")
    public ResponseEntity<ResponseBodyWrapper<Void>> deleteEssayTopic(@PathVariable long variantId) {
        try {
            this.testService.deleteEssayTopicByVariantId(variantId);
            return ResponseEntity.ok(ResponseBodyWrapper.success());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        }
    }

}
