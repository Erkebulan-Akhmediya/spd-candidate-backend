package kz.afm.candidate.test.option;

import kz.afm.candidate.test.dto.CreateOptionRequest;
import kz.afm.candidate.test.question.QuestionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public void create(QuestionEntity question, List<CreateOptionRequest> dtos) {
        List<OptionEntity> options = new LinkedList<>();
        if (dtos == null || dtos.isEmpty()) return;

        dtos.forEach((CreateOptionRequest dto) -> {
            try {
                options.add(
                        new OptionEntity(
                                dto.isWithFile(),
                                dto.getFileName(),
                                dto.getNameRus(),
                                dto.getNameKaz(),
                                dto.getIsCorrect(),
                                question
                        )
                );
            } catch (Exception e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Ошибка сохранения файла");
            }
        });
        this.optionRepository.saveAll(options);
    }

    public List<OptionEntity> getAllByQuestion(QuestionEntity question) {
        return this.optionRepository.findAllByQuestion(question, Sort.by("id"));
    }

}
