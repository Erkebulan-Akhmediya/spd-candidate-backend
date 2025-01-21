package kz.afm.candidate.test;

import kz.afm.candidate.test.dto.CreateTestRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
