package kz.afm.candidate.candidate.education.type;

import kz.afm.candidate.candidate.education.type.dto.EducationTypeResponseDto;
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
    public ResponseEntity<ResponseBodyWrapper<List<EducationTypeResponseDto>>> getAll() {
        try {
            final List<EducationTypeEntity> types = this.educationTypeService.getAll();
            final List<EducationTypeResponseDto> typeDtos = types.stream().map(EducationTypeResponseDto::new).toList();
            return ResponseEntity.ok(ResponseBodyWrapper.success(typeDtos));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

}
