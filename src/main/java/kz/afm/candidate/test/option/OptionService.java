package kz.afm.candidate.test.option;

import kz.afm.candidate.test.dto.CreateOptionRequest;
import kz.afm.candidate.test.question.QuestionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OptionService {

    private final OptionRepository optionRepository;

    public void create(QuestionEntity question, List<CreateOptionRequest> dtos) {
        List<OptionEntity> options = new LinkedList<>();
        if (dtos == null || dtos.isEmpty()) return;

        dtos.forEach((CreateOptionRequest dto) -> options.add(
                new OptionEntity(
                        dto.isWithFile(),
                        UUID.randomUUID(),
                        dto.getNameRus(),
                        dto.getNameKaz(),
                        dto.getIsCorrect(),
                        question
                )
        ));
        this.optionRepository.saveAll(options);
    }

}
