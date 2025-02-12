package kz.afm.candidate.test.evaluation.scale;

import kz.afm.candidate.test.TestEntity;
import kz.afm.candidate.test.dto.evaluation.CreateScaleRequest;
import kz.afm.candidate.test.evaluation.section.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScaleService {

    private final SectionService sectionService;
    private final ScaleRepository scaleRepository;

    public void create(TestEntity test, List<CreateScaleRequest> scaleDtoList) {
        scaleDtoList.forEach((CreateScaleRequest scaleDto) -> {
            final ScaleEntity newScale = new ScaleEntity(
                    scaleDto.getNameRus(),
                    scaleDto.getNameKaz(),
                    test
            );
            final ScaleEntity savedScale = this.scaleRepository.save(newScale);
            this.sectionService.create(savedScale, scaleDto.getSections());
        });
    }

    public ScaleEntity getByTestAndIndex(TestEntity test, int index) {
        List<ScaleEntity> scales = this.scaleRepository.findAllByTest(test, Sort.by("id"));
        return scales.get(index);
    }

}
