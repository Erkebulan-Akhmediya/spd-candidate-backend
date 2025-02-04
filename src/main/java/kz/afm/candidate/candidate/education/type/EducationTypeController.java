package kz.afm.candidate.candidate.education.type;

import kz.afm.candidate.candidate.education.type.dto.EducationTypeResponseBody;
import kz.afm.candidate.dto.ResponseBodyWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("education/type")
@RestController
public class EducationTypeController {

    private final EducationTypeService educationTypeService;

    @GetMapping("all")
    public ResponseEntity<ResponseBodyWrapper<List<EducationTypeResponseBody>>> getAll() {
        try {
            final List<EducationTypeEntity> entities = this.educationTypeService.getAll();
            final List<EducationTypeResponseBody> types = EducationTypeResponseBody.fromEntities(entities);
            return ResponseEntity.ok(ResponseBodyWrapper.success(types));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

}
