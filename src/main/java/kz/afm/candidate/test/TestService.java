package kz.afm.candidate.test;

import jakarta.transaction.Transactional;
import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.candidate.CandidateService;
import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityEntity;
import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityService;
import kz.afm.candidate.test.dto.CreateQuestionRequest;
import kz.afm.candidate.test.dto.CreateTestRequest;
import kz.afm.candidate.test.dto.CreateVariantRequest;
import kz.afm.candidate.test.question.QuestionEntity;
import kz.afm.candidate.test.session.TestSessionEntity;
import kz.afm.candidate.test.session.TestSessionService;
import kz.afm.candidate.test.session.evaluation.scale.ScaleService;
import kz.afm.candidate.test.session.evaluation.section.conditional.variable.ConditionalSectioningVariableService;
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
    private final ConditionalSectioningVariableService conditionalSectioningVariableService;

    private final TestRepository testRepository;

    @Transactional
    public void create(CreateTestRequest testDto) throws RuntimeException {
        final TestEntity test = this.save(testDto);

        if (test.conditionallySectioned) {
            this.conditionalSectioningVariableService.create(test, testDto.conditionalVars);
        }
        this.scaleService.create(test, testDto.scales);
        this.variantService.create(test, testDto.variants);

        final int pointDistributionTestType = 5;
        if (testDto.type == pointDistributionTestType) {
            this.pointDistributionTestService.create(test, testDto.maxPointsPerQuestion);
        }
    }

    private TestEntity save(CreateTestRequest testDto) throws RuntimeException {
        final TestTypeEntity type = this.testTypeService.getById(testDto.type);
        final Set<AreaOfActivityEntity> areasOfActivity = this.areaOfActivityService.getSetOfAllByNames(testDto.areasOfActivities);
        final TestEntity test = new TestEntity(
                testDto.nameRus,
                testDto.nameKaz,
                testDto.descriptionRus,
                testDto.descriptionKaz,
                Boolean.parseBoolean(testDto.isLimitless),
                testDto.duration,
                areasOfActivity,
                type,
                testDto.conditionallySectioned
        );
        return this.testRepository.save(test);
    }

    public List<TestEntity> getAll(UserEntity requestingUser, int pageNumber, int pageSize) {
        final CandidateEntity requestingCandidate = this.candidateService.getByUserOrNull(requestingUser);
        if (requestingCandidate != null) return this.getAllForCandidate(requestingCandidate);

        final boolean ignorePagination = pageSize == -1;
        if (ignorePagination) return this.testRepository.findAllByDeletedIsFalse();

        final PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return this.testRepository.findAllByDeletedIsFalse(pageRequest).getContent();
    }

    private List<TestEntity> getAllForCandidate(CandidateEntity candidate) {
        final Set<AreaOfActivityEntity> areas = new LinkedHashSet<>();
        areas.add(candidate.getAreaOfActivity());
        final Set<Long> passedTestIds = this.getAllPassed(candidate);
        return this.testRepository.findAllByAreaOfActivitiesContainingAndDeleted(areas, false)
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
        return this.testRepository.countAllByDeleted(false);
    }

    public TestEntity getById(long id) {
        return this.testRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Не найден тест с ID: " + id)
        );
    }

    public List<QuestionEntity> getEssayTopics() throws NoSuchElementException {
        final TestEntity essay = this.testRepository.findAllByNameRus("Эссе").getFirst();
        return this.variantService.getQuestionsByTestId(essay.getId());
    }

    @Transactional
    public void createEssayTopic(String nameRus, String nameKaz) {
        final CreateQuestionRequest question = new CreateQuestionRequest(
                false,
                null,
                nameRus,
                nameKaz,
                false,
                0,
                null
        );
        final List<CreateQuestionRequest> questions = new LinkedList<>();
        questions.add(question);

        final CreateVariantRequest variant = new CreateVariantRequest(questions);
        final List<CreateVariantRequest> variants = new LinkedList<>();
        variants.add(variant);

        final TestEntity essay = this.testRepository.findAllByNameRus("Эссе").getFirst();
        this.variantService.create(essay, variants);
    }

    @Transactional
    public void deleteEssayTopicByVariantId(long variantId) {
        this.variantService.deleteVariantById(variantId);
    }

    @Transactional
    public void updateEssayTopicByVariantId(long variantId, String nameRus, String nameKaz) {
        this.variantService.updateEssayTopicByVariantId(variantId, nameRus, nameKaz);
    }

}
