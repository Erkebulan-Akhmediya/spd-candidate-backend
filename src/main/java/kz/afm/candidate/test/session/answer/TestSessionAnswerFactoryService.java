package kz.afm.candidate.test.session.answer;

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
public class TestSessionAnswerFactoryService {

    private final OptionService optionService;

    public List<TestSessionAnswerEntity> create(
            TestSessionEntity testSession,
            TestSessionAnswerRequest answerDto
    ) {
        final Object answer = answerDto.getAnswer();

        if (this.answerNonExistent(answer)) return new LinkedList<>();
        if (this.answerForOpenQuestion(answer)) {
            TestSessionAnswerEntity openAnswer = this.createAnswerForOpenQuestion(testSession, answer);
            return new LinkedList<>() {{
                add(openAnswer);
            }};
        }
        if (this.answerForMcqWithOneOrNoCorrect(answer)) {
            TestSessionAnswerEntity mcqAnswer = this.createMCQAnswer(testSession, (Number) answer);
            return new LinkedList<>() {{
                add(mcqAnswer);
            }};
        }
        if (this.answerForMcqWithMultipleCorrect(answer)) {
            return this.createMultipleMCQAnswers(testSession, (List<Number>) answer);
        }
        if (this.answerForPointDistribution(answer)) {
            return this.createAnswerForPointDistribution(testSession, (List<Map<String, Number>>) answer);
        }
        throw new RuntimeException("Не поддерживаемый тип ответа: " + answer);

    }

    private boolean answerNonExistent(Object answer) {
        return answer == null || (answer instanceof List<?> && ((List<?>) answer).isEmpty());
    }

    private boolean answerForOpenQuestion(Object answer) {
        return answer instanceof String;
    }

    private boolean answerForMcqWithOneOrNoCorrect(Object answer) {
        return answer instanceof Number;
    }

    private TestSessionAnswerEntity createAnswerForOpenQuestion(TestSessionEntity testSession, Object answer) {
        return new TestSessionAnswerEntity(testSession, (String) answer);
    }

    private boolean answerForMcqWithMultipleCorrect(Object answer) {
        return answer instanceof List<?> &&
                !((List<?>) answer).isEmpty()
                && ((List<?>) answer).getFirst() instanceof Number;
    }

    private boolean answerForPointDistribution(Object answer) {
        return answer instanceof List<?> &&
                !((List<?>) answer).isEmpty()
                && ((List<?>) answer).getFirst() instanceof Map;
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

}
