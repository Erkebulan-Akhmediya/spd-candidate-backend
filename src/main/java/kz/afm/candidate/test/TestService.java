package kz.afm.candidate.test;

import jakarta.transaction.Transactional;
import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityEntity;
import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityService;
import kz.afm.candidate.test.dto.CreateTestRequest;
import kz.afm.candidate.test.session.evaluation.scale.ScaleService;
import kz.afm.candidate.test.test_type.TestTypeEntity;
import kz.afm.candidate.test.test_type.TestTypeService;
import kz.afm.candidate.test.test_type.point_distribution.PointDistributionTestService;
import kz.afm.candidate.test.variant.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class TestService {

    private final VariantService variantService;
    private final AreaOfActivityService areaOfActivityService;
    private final TestTypeService testTypeService;
    private final PointDistributionTestService pointDistributionTestService;
    private final ScaleService scaleService;

    private final TestRepository testRepository;

    @Transactional
    public void create(CreateTestRequest testDto) throws RuntimeException {
        final TestEntity test = this.save(testDto);

        this.scaleService.create(test, testDto.getScales());
        this.variantService.create(test, testDto.getVariants());

        final int pointDistributionTestType = 5;
        if (testDto.getType() == pointDistributionTestType) {
            this.pointDistributionTestService.create(test, testDto.getMaxPointsPerQuestion());
        }
    }

    private TestEntity save(CreateTestRequest testDto) throws RuntimeException {
        final TestTypeEntity type = this.testTypeService.getById(testDto.getType());
        return this.testRepository.save(
                new TestEntity(
                        testDto.getNameRus(),
                        testDto.getNameKaz(),
                        Boolean.parseBoolean(testDto.getIsLimitless()),
                        testDto.getDuration(),
                        this.areaOfActivityService.getAllSetByNames(testDto.getAreasOfActivities()),
                        type
                )
        );
    }

    public List<TestEntity> getAll(int pageNumber, int pageSize, String areaOfActivity) {
        final Set<AreaOfActivityEntity> areas = new LinkedHashSet<>() {{
            add(new AreaOfActivityEntity(areaOfActivity));
        }};

        final boolean ignoreAreas = Objects.equals(areaOfActivity, "any");
        final boolean ignorePagination= pageSize == -1;

        if (ignorePagination) {
            if (ignoreAreas) return this.testRepository.findAll();
            return this.testRepository.findAllByAreaOfActivitiesContaining(areas);
        }

        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        if (ignoreAreas) return this.testRepository.findAll(pageRequest).getContent();
        return this.testRepository.findAllByAreaOfActivitiesContaining(areas, pageRequest);
    }

    public long getAllCount() {
        return this.testRepository.count();
    }

    public TestEntity getById(long id) {
        return this.testRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Не найден тест с ID: " + id)
        );
    }

    public int getTypeIdByTestId(long testId) throws NoSuchElementException {
        return this.getById(testId).getType().getId();
    }

}
