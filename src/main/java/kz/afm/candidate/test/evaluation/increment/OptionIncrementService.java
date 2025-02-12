package kz.afm.candidate.test.evaluation.increment;

import kz.afm.candidate.test.TestEntity;
import kz.afm.candidate.test.dto.evaluation.CreateOptionIncrementRequest;
import kz.afm.candidate.test.evaluation.scale.ScaleEntity;
import kz.afm.candidate.test.evaluation.scale.ScaleService;
import kz.afm.candidate.test.option.OptionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OptionIncrementService {

    private final ScaleService scaleService;
    private final OptionIncrementRepository optionIncrementRepository;

    public void create(OptionEntity option, CreateOptionIncrementRequest incrementDto) {
        final TestEntity optionTest = option.getQuestion().getVariant().getTest();
        final int zeroBasedScaleIndex = incrementDto.getScaleIndex() - 1;
        final ScaleEntity optionScale = this.scaleService.getByTestAndIndex(optionTest, zeroBasedScaleIndex);
        final OptionIncrementEntity newIncrement = new OptionIncrementEntity(
                option,
                optionScale,
                incrementDto.getScore()
        );
        this.optionIncrementRepository.save(newIncrement);
    }

}
