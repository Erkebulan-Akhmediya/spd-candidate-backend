package kz.afm.candidate.test.session.answer;

import jakarta.transaction.Transactional;
import kz.afm.candidate.test.option.OptionEntity;
import kz.afm.candidate.test.option.OptionService;
import kz.afm.candidate.test.session.TestSessionEntity;
import kz.afm.candidate.test.session.dto.TestSessionAnswerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TestSessionAnswerService {

    private final OptionService optionService;
    private final TestSessionAnswerRepository testSessionAnswerRepository;

    @Transactional
    public void save(TestSessionEntity testSession, List<TestSessionAnswerRequest> answerDtoList) {
        List<TestSessionAnswerEntity> answers = new LinkedList<>();

        answerDtoList.forEach((TestSessionAnswerRequest answerDto) -> {
            final List<TestSessionAnswerEntity> answersFromDto = this.createFromDto(testSession, answerDto);
            answers.addAll(answersFromDto);
        });

        this.testSessionAnswerRepository.saveAll(answers);
    }

    private List<TestSessionAnswerEntity> createFromDto(
            TestSessionEntity testSession,
            TestSessionAnswerRequest answerDto
    ) {
        final Object answer = answerDto.getAnswer();

        final boolean answerNonExistent = answer == null ||
                (answer instanceof List<?> && ((List<?>) answer).isEmpty());

        final boolean answerForOpenQuestion = answer instanceof String;

        final boolean answerForMcqWithOneOrNoCorrect = answer instanceof Number;

        final boolean answerForMcqWithMultipleCorrect = answer instanceof List<?> &&
                !((List<?>) answer).isEmpty()
                && ((List<?>) answer).getFirst() instanceof Number;

        final boolean answerForPointDistribution = answer instanceof List<?> &&
                !((List<?>) answer).isEmpty()
                && ((List<?>) answer).getFirst() instanceof Map;

        if (answerNonExistent) return new LinkedList<>();
        if (answerForOpenQuestion) {
            TestSessionAnswerEntity openAnswer = this.createAnswerForOpenQuestion(testSession, answer);
            return new LinkedList<>() {{
                add(openAnswer);
            }};
        }
        if (answerForMcqWithOneOrNoCorrect) {
            TestSessionAnswerEntity mcqAnswer = this.createMCQAnswer(testSession, (Number) answer);
            return new LinkedList<>() {{
                add(mcqAnswer);
            }};
        }
        if (answerForMcqWithMultipleCorrect) {
            return this.createMultipleMCQAnswers(testSession, (List<Number>) answer);
        }
        if (answerForPointDistribution) {
            return this.createAnswerForPointDistribution(testSession, (List<Map<String, Number>>) answer);
        }
        throw new RuntimeException("Не поддерживаемый тип ответа: " + answer);

    }

    private TestSessionAnswerEntity createAnswerForOpenQuestion(TestSessionEntity testSession, Object answer) {
        return new TestSessionAnswerEntity(testSession, (String) answer);
    }

    private List<TestSessionAnswerEntity> createMultipleMCQAnswers(TestSessionEntity testSession, List<Number> answerList) {
        return answerList
                .stream()
                .map((Number optionId) -> this.createMCQAnswer(testSession, optionId))
                .toList();
    }

    private TestSessionAnswerEntity createMCQAnswer(TestSessionEntity testSession, Number optionId) {
        final OptionEntity answerOption = this.optionService.getById(optionId.longValue());
        return new TestSessionAnswerEntity(testSession, answerOption);
    }

    private List<TestSessionAnswerEntity> createAnswerForPointDistribution(
            TestSessionEntity testSession,
            List<Map<String, Number>> answer
    ) {
        return answer
                .stream()
                .map((Map<String, Number> answerItem) -> this.createAnswerItemPointDistribution(testSession, answerItem))
                .toList();
    }

    private TestSessionAnswerEntity createAnswerItemPointDistribution(
            TestSessionEntity testSession,
            Map<String, Number> answerItem
    ) {
        final OptionEntity answerOption = this.optionService.getById(answerItem.get("optionId").longValue());
        return new TestSessionAnswerEntity(
                testSession,
                answerOption,
                String.valueOf(answerItem.get("point"))
        );
    }

    public List<TestSessionAnswerEntity> getAllByTestSessionId(TestSessionEntity testSession) {
        return this.testSessionAnswerRepository.findAllByTestSession(testSession);
    }

}
