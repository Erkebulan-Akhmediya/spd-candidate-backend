package kz.afm.candidate.test.question.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionTypeService {

    private final QuestionTypeRepository questionTypeRepository;

    public List<QuestionTypeEntity> getAll() {
        return this.questionTypeRepository.findAll();
    }

}
