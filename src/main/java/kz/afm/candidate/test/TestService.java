package kz.afm.candidate.test;

import jakarta.transaction.Transactional;
import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityService;
import kz.afm.candidate.test.dto.CreateTestRequest;
import kz.afm.candidate.test.variant.VariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
                        this.areaOfActivityService.getAllSetByNameList(dto.getAreasOfActivities())
                )
        );
        this.variantService.create(test, dto.getVariants());
    }

    public List<TestEntity> getAll() {
        return this.testRepository.findAll();
    }

}
