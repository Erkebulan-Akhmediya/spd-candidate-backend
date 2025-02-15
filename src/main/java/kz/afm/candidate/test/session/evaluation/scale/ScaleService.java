package kz.afm.candidate.test.session.evaluation.scale;

import kz.afm.candidate.test.TestEntity;
import kz.afm.candidate.test.dto.evaluation.CreateScaleRequest;
import kz.afm.candidate.test.session.evaluation.section.SectionService;
import kz.afm.candidate.test.session.TestSessionEntity;
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
        List<ScaleEntity> scales = this.getAllByTest(test);
        return scales.get(index);
    }

    public List<ScaleEntity> getAllByTestSession(TestSessionEntity testSession) {
        return this.getAllByTest(testSession.getVariant().getTest());
    }

    public List<ScaleEntity> getAllByTest(TestEntity test) {
        return this.scaleRepository.findAllByTest(test, Sort.by("id"));
    }

}
