package kz.afm.candidate.test.question.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseBody {
    public long id;
    public String nameRus;
    public String nameKaz;
    public boolean withFile;
    public String fileUrl;
    public List<OptionResponseBody> options;
}
