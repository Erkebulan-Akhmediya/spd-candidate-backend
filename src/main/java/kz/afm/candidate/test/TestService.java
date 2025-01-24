package kz.afm.candidate.test;

import jakarta.transaction.Transactional;
import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityEntity;
import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityService;
import kz.afm.candidate.test.dto.CreateTestRequest;
import kz.afm.candidate.test.variant.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TestService {

    private final VariantService variantService;
    private final TestRepository testRepository;
    private final AreaOfActivityService areaOfActivityService;

    @Transactional
    public void create(CreateTestRequest dto) throws RuntimeException {
        final TestEntity test = this.testRepository.save(
                new TestEntity(
                        dto.getNameRus(),
                        dto.getNameKaz(),
                        Boolean.parseBoolean(dto.getIsLimitless()),
                        dto.getDuration(),
                        new LinkedHashSet<>(this.areaOfActivityService.getAllSetByNames(dto.getAreasOfActivities()))
                )
        );
        this.variantService.create(test, dto.getVariants());
    }

    public List<TestEntity> getAll(int pageNumber, int pageSize, String areaOfActivity) {
        final Set<AreaOfActivityEntity> areas = new LinkedHashSet<>() {{
            add(new AreaOfActivityEntity(areaOfActivity));
        }};
        final boolean ignoreAreas = Objects.equals(areaOfActivity, "any");

        if (pageSize == -1) {
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
                () -> new RuntimeException("Не найден тест с ID: " + id)
        );
    }

}
