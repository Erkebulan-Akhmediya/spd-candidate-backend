package kz.afm.candidate.test.session.answer;

import kz.afm.candidate.test.option.OptionEntity;
import kz.afm.candidate.test.option.OptionService;
import kz.afm.candidate.test.session.TestSessionEntity;
import kz.afm.candidate.test.session.answer.dto.AnswerDTOForMCQWithMultipleCorrect;
import kz.afm.candidate.test.session.answer.dto.AnswerDTOForPointDistribution;
import kz.afm.candidate.test.session.answer.dto.AnswerDTOItemForPointDistribution;
import kz.afm.candidate.test.session.dto.TestSessionAnswerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TestSessionAnswerService {

    private final OptionService optionService;
    private final TestSessionAnswerRepository testSessionAnswerRepository;

    public void save(TestSessionEntity testSession, List<TestSessionAnswerRequest> answerDtoList) {
        List<TestSessionAnswerEntity> answers = new LinkedList<>();

        answerDtoList.forEach(
                (TestSessionAnswerRequest answerDto) -> answers.addAll(this.createFromDto(testSession, answerDto))
        );

        this.testSessionAnswerRepository.saveAll(answers);
    }

    private List<TestSessionAnswerEntity> createFromDto(TestSessionEntity testSession, TestSessionAnswerRequest answerDto) {
        final Object answer = answerDto.getAnswer();

        final boolean answerNonExistent = answer == null;
        final boolean answerForOpenQuestion = answer instanceof String;
        final boolean answerForMcqWithOneOrNoCorrect = answer instanceof Long;
        final boolean answerForMcqWithMultipleCorrect = answer instanceof AnswerDTOForMCQWithMultipleCorrect;
        final boolean answerForPointDistribution = answer instanceof AnswerDTOForPointDistribution;

        if (answerNonExistent) return new LinkedList<>();
        if (answerForOpenQuestion) {
            TestSessionAnswerEntity openAnswer = this.createAnswerForOpenQuestion(testSession, answer);
            return new LinkedList<>() {{ add(openAnswer); }};
        }
        if (answerForMcqWithOneOrNoCorrect) {
            TestSessionAnswerEntity mcqAnswer = this.createMCQAnswer(testSession, answer);
            return new LinkedList<>() {{ add(mcqAnswer); }};
        }
        if (answerForMcqWithMultipleCorrect) {
            return this.createMultipleMCQAnswers(testSession, answer);
        }
        if (answerForPointDistribution) {
            return this.createAnswerForPointDistribution(testSession, answer);
        }
        throw new RuntimeException("Не поддерживаемый тип ответа: " + answer);

    }

    private TestSessionAnswerEntity createAnswerForOpenQuestion(TestSessionEntity testSession, Object answer) {
        return new TestSessionAnswerEntity(testSession, (String) answer);
    }

    private List<TestSessionAnswerEntity> createMultipleMCQAnswers(TestSessionEntity testSession, Object answerList) {
        return ((AnswerDTOForMCQWithMultipleCorrect) answerList)
                .stream()
                .map((Long optionId) -> this.createMCQAnswer(testSession, optionId))
                .toList();
    }

    private TestSessionAnswerEntity createMCQAnswer(TestSessionEntity testSession, Object optionId) {
        final OptionEntity answerOption = this.optionService.getById((Long) optionId);
        return new TestSessionAnswerEntity(testSession, answerOption);
    }

    private List<TestSessionAnswerEntity> createAnswerForPointDistribution(TestSessionEntity testSession, Object answer) {
        return ((AnswerDTOForPointDistribution) answer)
                .stream()
                .map((AnswerDTOItemForPointDistribution answerItem) -> this.createAnswerItemPointDistribution(testSession, answerItem))
                .toList();
    }

    private TestSessionAnswerEntity createAnswerItemPointDistribution(
            TestSessionEntity testSession,
            AnswerDTOItemForPointDistribution answerItem
    ) {
        final OptionEntity answerOption = this.optionService.getById(answerItem.optionId);
        return new TestSessionAnswerEntity(
                testSession,
                answerOption,
                String.valueOf(answerItem.point)
        );
    }

}
