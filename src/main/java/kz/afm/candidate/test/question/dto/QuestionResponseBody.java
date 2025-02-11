package kz.afm.candidate.test.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QuestionResponseBody {
    private long id;
    private String nameRus;
    private String nameKaz;
    private boolean withFile;
    private String fileUrl;
    private List<OptionResponseBody> options;
}
