package kz.afm.candidate.test.evaluation.section;

import kz.afm.candidate.test.dto.evaluation.CreateScaleSectionRequest;
import kz.afm.candidate.test.evaluation.scale.ScaleEntity;
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
                                sectionDto.getDescriptionRus(),
                                sectionDto.getDescriptionKaz(),
                                sectionDto.getLowerBound(),
                                sectionDto.getUpperBound(),
                                scale
                        )
                )
                .toList();
        this.sectionRepository.saveAll(sections);
    }

}
