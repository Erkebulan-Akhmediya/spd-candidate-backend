package kz.afm.candidate.test;

import jakarta.transaction.Transactional;
import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.candidate.CandidateService;
import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityEntity;
import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityService;
import kz.afm.candidate.test.dto.CreateTestRequest;
import kz.afm.candidate.test.session.TestSessionEntity;
import kz.afm.candidate.test.session.TestSessionService;
import kz.afm.candidate.test.session.evaluation.scale.ScaleService;
import kz.afm.candidate.test.test_type.TestTypeEntity;
import kz.afm.candidate.test.test_type.TestTypeService;
import kz.afm.candidate.test.test_type.point_distribution.PointDistributionTestService;
import kz.afm.candidate.test.variant.VariantService;
import kz.afm.candidate.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TestService {

    private final VariantService variantService;
    private final AreaOfActivityService areaOfActivityService;
    private final TestTypeService testTypeService;
    private final PointDistributionTestService pointDistributionTestService;
    private final ScaleService scaleService;
    private final CandidateService candidateService;
    private final TestSessionService testSessionService;

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

    public List<TestEntity> getAll(UserEntity requestingUser, int pageNumber, int pageSize) {
        final CandidateEntity requestingCandidate = this.candidateService.getByUserOrNull(requestingUser);
        if (requestingCandidate != null) return this.getAllForCandidate(requestingCandidate);

        final boolean ignorePagination = pageSize == -1;
        if (ignorePagination) return this.testRepository.findAll();

        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return this.testRepository.findAll(pageRequest).getContent();
    }

    private List<TestEntity> getAllForCandidate(CandidateEntity candidate) {
        final Set<AreaOfActivityEntity> areas = new LinkedHashSet<>() ;
        areas.add(candidate.getAreaOfActivity());
        final Set<Long> passedTestIds = this.getAllPassed(candidate);
        return this.testRepository.findAllByAreaOfActivitiesContaining(areas)
                .stream()
                .filter((TestEntity test) -> !passedTestIds.contains(test.getId()))
                .toList();
    }

    private Set<Long> getAllPassed(CandidateEntity candidate) {
        return this.testSessionService.getAllByCandidate(candidate)
                .stream()
                .map((TestSessionEntity testSession) -> testSession.getVariant().getTest().getId())
                .collect(Collectors.toSet());
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
