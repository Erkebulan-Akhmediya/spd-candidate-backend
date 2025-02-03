package kz.afm.candidate.file;

import kz.afm.candidate.dto.ResponseBodyWrapper;
import kz.afm.candidate.file.dto.FileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("file")
@RestController
public class FileController {

    private final FileService fileService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseBodyWrapper<Void>> save(@ModelAttribute FileRequest request) {
        try {
            this.fileService.save(request.getFile());
            return ResponseEntity.ok(ResponseBodyWrapper.success());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

}
