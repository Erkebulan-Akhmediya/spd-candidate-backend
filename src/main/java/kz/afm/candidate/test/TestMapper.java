package kz.afm.candidate.test;

import kz.afm.candidate.test.dto.TestDto;
import kz.afm.candidate.test.dto.VariantDto;
import kz.afm.candidate.test.dto.evaluation.ConditionalSectioningVariableDto;
import kz.afm.candidate.test.dto.evaluation.ScaleDto;
import kz.afm.candidate.test.session.evaluation.scale.ScaleEntity;
import kz.afm.candidate.test.session.evaluation.scale.ScaleMapper;
import kz.afm.candidate.test.session.evaluation.scale.ScaleService;
import kz.afm.candidate.test.session.evaluation.section.conditional.variable.ConditionalSectioningVariableEntity;
import kz.afm.candidate.test.session.evaluation.section.conditional.variable.ConditionalSectioningVariableService;
import kz.afm.candidate.test.test_type.point_distribution.PointDistributionTestService;
import kz.afm.candidate.test.variant.VariantEntity;
import kz.afm.candidate.test.variant.VariantMapper;
import kz.afm.candidate.test.variant.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TestMapper {

    private final PointDistributionTestService pointDistributionTestService;
    private final ConditionalSectioningVariableService conditionalSectioningVariableService;
    private final ScaleService scaleService;
    private final ScaleMapper scaleMapper;
    private final VariantService variantService;
    private final VariantMapper variantMapper;

    public TestDto toDto(TestEntity test) {

        int maxPointsPerQuestion = 0;
        if (test.getType().getId() == PointDistributionTestService.TYPE_ID) {
            maxPointsPerQuestion = this.pointDistributionTestService.getMaxPointsPerQuestionByTest(test);
        }

        final List<ConditionalSectioningVariableDto> varDtos = this.conditionalSectioningVariableService
                .getAllByTest(test)
                .stream()
                .map(
                        (ConditionalSectioningVariableEntity var) -> new ConditionalSectioningVariableDto(
                                var.name,
                                this.conditionalSectioningVariableService.getTypeIndex(var.type),
                                var.reference
                        )
                )
                .toList();

        final List<ScaleEntity> scales = this.scaleService.getAllByTest(test);
        final List<Long> scaleIds = scales.stream().map(ScaleEntity::getId).toList();
        final List<ScaleDto> scaleDtos = new LinkedList<>();
        for (int i = 0; i < scales.size(); i++) {
            final ScaleEntity scale = scales.get(i);
            final ScaleDto scaleDto = this.scaleMapper.toDto(i, scale);
            scaleDtos.add(scaleDto);
        }

        final List<VariantDto> variantDtos = this.variantService.getByTestId(test.getId())
                .stream()
                .map(
                        (VariantEntity variant) -> this.variantMapper.toDto(variant, scaleIds)
                )
                .toList();

        return new TestDto(test, variantDtos, maxPointsPerQuestion, varDtos, scaleDtos);
    }

}
