package kz.afm.candidate.test.test_type;

import kz.afm.candidate.dto.ResponseBodyWrapper;
import kz.afm.candidate.test.test_type.dto.TestTypeResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("test/type")
@RestController
public class TestTypeController {

    private final TestTypeService testTypeService;

    @GetMapping("all")
    public ResponseEntity<ResponseBodyWrapper<List<TestTypeResponseBody>>> getAll() {
        try {
            final List<TestTypeEntity> types = this.testTypeService.getAll();
            final List<TestTypeResponseBody> typeDtoList = types.stream().map(TestTypeResponseBody::new).toList();
            return ResponseEntity.ok(ResponseBodyWrapper.success(typeDtoList));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

}
