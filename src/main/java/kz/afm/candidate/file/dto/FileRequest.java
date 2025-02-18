package kz.afm.candidate.file.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
public class FileRequest {
    public MultipartFile file;
}
