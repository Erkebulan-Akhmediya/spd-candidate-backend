package kz.afm.candidate.test.session.evaluation.section;

import kz.afm.candidate.test.dto.evaluation.CreateScaleSectionRequest;
import kz.afm.candidate.test.session.evaluation.result.ResultEntity;
import kz.afm.candidate.test.session.evaluation.scale.ScaleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    public void create(ScaleEntity scale, List<CreateScaleSectionRequest> sectionDtoList) {
        final List<SectionEntity> sections = sectionDtoList.stream()
                .map(
                        (CreateScaleSectionRequest sectionDto) -> new SectionEntity(
                                sectionDto.descriptionRus,
                                sectionDto.descriptionKaz,
                                sectionDto.lowerBound,
                                sectionDto.upperBound,
                                scale
                        )
                )
                .toList();
        this.sectionRepository.saveAll(sections);
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
