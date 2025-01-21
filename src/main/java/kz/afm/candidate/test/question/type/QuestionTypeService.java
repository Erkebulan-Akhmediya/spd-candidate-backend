package kz.afm.candidate.test.question.type;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class QuestionTypeService {

    private final QuestionTypeRepository questionTypeRepository;

    public List<QuestionTypeEntity> getAll() {
        return this.questionTypeRepository.findAll();
    }

    public QuestionTypeEntity getById(int id) throws NoSuchElementException {
        return this.questionTypeRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Тип вопроса с ID: " + id + " не найден")
        );
    }

}
