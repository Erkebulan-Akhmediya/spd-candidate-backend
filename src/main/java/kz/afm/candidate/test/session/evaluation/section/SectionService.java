package kz.afm.candidate.test.session.evaluation.section;

import kz.afm.candidate.test.dto.evaluation.CreateScaleSectionRequest;
import kz.afm.candidate.test.session.evaluation.result.ResultEntity;
import kz.afm.candidate.test.session.evaluation.scale.ScaleEntity;
import kz.afm.candidate.test.session.evaluation.section.conditional.condition.SectioningConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SectionService {

    private final SectioningConditionService sectioningConditionService;

    private final SectionRepository sectionRepository;

    public void create(ScaleEntity scale, List<CreateScaleSectionRequest> sectionDtoList) {
        sectionDtoList.forEach(
                (CreateScaleSectionRequest sectionDto) -> {
                    SectionEntity section = new SectionEntity(
                            sectionDto.descriptionRus,
                            sectionDto.descriptionKaz,
                            sectionDto.lowerBound,
                            sectionDto.upperBound,
                            scale
                    );
                    section = this.sectionRepository.save(section);
                    if (section.getScale().getTest().conditionallySectioned) {
                        this.sectioningConditionService.create(section, sectionDto.conditions);
                    }
                }
        );

    }

    public SectionEntity getByResult(ResultEntity result) {
        return this.sectionRepository
                .findAllByScaleAndLowerBoundLessThanEqualAndUpperBoundGreaterThanEqual(
                        result.getScale(),
                        result.getScore(),
                        result.getScore()
                )
                .orElse(null);
    }

}
