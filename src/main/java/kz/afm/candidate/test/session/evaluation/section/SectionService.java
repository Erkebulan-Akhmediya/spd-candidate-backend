package kz.afm.candidate.test.session.evaluation.section;

import kz.afm.candidate.test.dto.evaluation.ScaleSectionDto;
import kz.afm.candidate.test.session.TestSessionEntity;
import kz.afm.candidate.test.session.evaluation.result.ResultEntity;
import kz.afm.candidate.test.session.evaluation.scale.ScaleEntity;
import kz.afm.candidate.test.session.evaluation.section.conditional.condition.SectioningConditionEntity;
import kz.afm.candidate.test.session.evaluation.section.conditional.condition.SectioningConditionService;
import kz.afm.candidate.test.session.evaluation.section.conditional.variable_value.ConditionalSectioningVariableValueEntity;
import kz.afm.candidate.test.session.evaluation.section.conditional.variable_value.ConditionalSectioningVariableValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class SectionService {

    private final SectioningConditionService sectioningConditionService;
    private final ConditionalSectioningVariableValueService conditionalSectioningVariableValueService;

    private final SectionRepository sectionRepository;

    public void create(ScaleEntity scale, List<ScaleSectionDto> sectionDtoList) {
        sectionDtoList.forEach(
                (ScaleSectionDto sectionDto) -> {
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
        final List<SectionEntity> sections = this.sectionRepository
                .findAllByScaleAndLowerBoundLessThanEqualAndUpperBoundGreaterThanEqual(
                        result.getScale(),
                        result.getScore(),
                        result.getScore()
                );
        if (sections.isEmpty()) {
            throw new NoSuchElementException("секции, удовлетворяющие условия, не найдены");
        }
        final TestSessionEntity testSession = result.getTestSession();
        if (!testSession.getVariant().getTest().conditionallySectioned) {
            return sections.getFirst();
        }
        return this.findValid(sections, testSession);
    }

    private SectionEntity findValid(List<SectionEntity> sections, TestSessionEntity testSession) {
        final Map<Long, ConditionalSectioningVariableValueEntity> varIdsAndValues =
                this.conditionalSectioningVariableValueService.getAllByTestSessionAsMap(testSession);
        for (SectionEntity section : sections) {
            if (this.isValidSection(section, varIdsAndValues)) return section;
        }
        throw new NoSuchElementException("секция, удовлетворяющая условия, не найдена");
    }

    private boolean isValidSection(
            SectionEntity section,
            Map<Long, ConditionalSectioningVariableValueEntity> varIdsAndValues
    ) {
        final List<SectioningConditionEntity> conditions = this.sectioningConditionService.getAllBySection(section);
        for (SectioningConditionEntity condition : conditions) {
            if (!this.sectioningConditionService.isTrue(condition, varIdsAndValues)) return false;
        }
        return true;
    }

    public List<SectionEntity> getAllByScale(ScaleEntity scale) {
        return this.sectionRepository.findAllByScale(scale);
    }

}
