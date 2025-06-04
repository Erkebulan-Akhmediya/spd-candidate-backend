package kz.afm.candidate.test.option;

import kz.afm.candidate.test.dto.OptionDto;
import kz.afm.candidate.test.dto.evaluation.OptionIncrementDto;
import kz.afm.candidate.test.session.evaluation.increment.OptionIncrementEntity;
import kz.afm.candidate.test.session.evaluation.increment.OptionIncrementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OptionMapper {

    private final OptionIncrementService optionIncrementService;

    public OptionDto toDto(OptionEntity option, List<Long> scaleIds) {
        final OptionIncrementEntity increment = this.optionIncrementService.getByOption(option);
        int scaleIndex = -1;
        for (int i = 0;i < scaleIds.size();i++) {
            if (increment.getScale().getId() == scaleIds.get(i)) {
                scaleIndex = i;
            }
        }
        final OptionIncrementDto incrementDto = new OptionIncrementDto(0, scaleIndex, increment.getIncrement());
        return new OptionDto(option, incrementDto);
    }

}
