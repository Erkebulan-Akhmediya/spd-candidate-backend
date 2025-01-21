package kz.afm.candidate.test.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOptionRequest {
    private boolean withFile;
    private MultipartFile file;
    private String nameRus;
    private String nameKaz;
    private Boolean isCorrect;
}
