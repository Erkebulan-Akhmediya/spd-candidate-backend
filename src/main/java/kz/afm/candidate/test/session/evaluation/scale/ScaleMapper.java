package kz.afm.candidate.test.session.evaluation.scale;

import kz.afm.candidate.test.dto.evaluation.ScaleDto;
import kz.afm.candidate.test.dto.evaluation.ScaleSectionDto;
import kz.afm.candidate.test.session.evaluation.section.SectionEntity;
import kz.afm.candidate.test.session.evaluation.section.SectionMapper;
import kz.afm.candidate.test.session.evaluation.section.SectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ScaleMapper {

    private final SectionService sectionService;
    private final SectionMapper sectionMapper;

    public ScaleDto toDto(int index, ScaleEntity scale) {
        final List<SectionEntity> sections = this.sectionService.getAllByScale(scale);
        final List<ScaleSectionDto> sectionDtos = new LinkedList<>();
        for (int i = 0; i < sections.size(); i++) {
            final SectionEntity section = sections.get(i);
            final ScaleSectionDto sectionDto = this.sectionMapper.toDto(i, section);
            sectionDtos.add(sectionDto);
        }
        return new ScaleDto(
                index,
                scale.getNameRus(),
                scale.getNameKaz(),
                sectionDtos
        );
    }

}
