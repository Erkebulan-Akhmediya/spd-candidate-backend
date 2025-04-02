package kz.afm.candidate.file;

import kz.afm.candidate.dto.ResponseBodyWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("file")
@RestController
public class FileController {

    private final FileService fileService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ResponseBodyWrapper<Void>> save(@RequestParam("file") MultipartFile file) {
        try {
            this.fileService.save(file);
            return ResponseEntity.ok(ResponseBodyWrapper.success());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error("Ошибка сервера"));
        }
    }

    @GetMapping
    public ResponseEntity<ResponseBodyWrapper<String>> getByName(@RequestParam String name) {
        try {
            final String base64 = this.fileService.getBase64Url(name);
            return ResponseEntity.ok(ResponseBodyWrapper.success(base64));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ResponseBodyWrapper.error(e.getMessage()));
        }
    }

}
