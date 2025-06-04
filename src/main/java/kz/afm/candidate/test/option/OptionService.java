package kz.afm.candidate.test.option;

import kz.afm.candidate.test.dto.OptionDto;
import kz.afm.candidate.test.session.evaluation.increment.OptionIncrementService;
import kz.afm.candidate.test.question.QuestionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class OptionService {

    private final OptionIncrementService optionIncrementService;
    private final OptionRepository optionRepository;

    public void create(QuestionEntity question, List<OptionDto> optionDtoList) {

        if (optionDtoList == null || optionDtoList.isEmpty()) return;

        optionDtoList.forEach((OptionDto optionDto) -> {
            final OptionEntity newOption = new OptionEntity(
                    optionDto.withFile,
                    optionDto.fileName,
                    optionDto.nameRus,
                    optionDto.nameKaz,
                    optionDto.isCorrect,
                    question
            );
            final OptionEntity savedOption = this.optionRepository.save(newOption);
            this.optionIncrementService.create(savedOption, optionDto.increment);
        });

    }

    public List<OptionEntity> getAllByQuestion(QuestionEntity question) {
        return this.optionRepository.findAllByQuestion(question, Sort.by("id"));
    }

    public OptionEntity getById(Long id) {
        return this.optionRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Вариант ответа с id " + id + " не найден")
        );
    }

}
