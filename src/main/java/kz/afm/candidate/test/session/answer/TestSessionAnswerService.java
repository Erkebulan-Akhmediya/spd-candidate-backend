package kz.afm.candidate.test.session.answer;

import jakarta.transaction.Transactional;
import kz.afm.candidate.test.question.QuestionEntity;
import kz.afm.candidate.test.question.QuestionService;
import kz.afm.candidate.test.session.TestSessionEntity;
import kz.afm.candidate.test.session.dto.TestSessionAnswerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class TestSessionAnswerService {

    private final TestSessionAnswerFactoryService testSessionAnswerFactoryService;
    private final QuestionService questionService;
    private final TestSessionAnswerRepository testSessionAnswerRepository;

    @Transactional
    public void save(TestSessionEntity testSession, List<TestSessionAnswerRequest> answerDtoList) {
        List<TestSessionAnswerEntity> answers = new LinkedList<>();

        answerDtoList.forEach((TestSessionAnswerRequest answerDto) -> {
            final QuestionEntity question = this.questionService.getById(answerDto.getQuestionId());
            final List<TestSessionAnswerEntity> answersFromDto = this.testSessionAnswerFactoryService.create(
                    testSession,
                    question,
                    answerDto
            );
            answers.addAll(answersFromDto);
        });

        this.testSessionAnswerRepository.saveAll(answers);
    }

    public List<TestSessionAnswerEntity> getAllByTestSession(TestSessionEntity testSession) {
        return this.testSessionAnswerRepository.findAllByTestSession(testSession);
    }

    public Map<Long, TestSessionAnswerEntity> getMapOfAllByTestSession(TestSessionEntity testSession) {
        final List<TestSessionAnswerEntity> answers = this.getAllByTestSession(testSession);
        final Map<Long, TestSessionAnswerEntity> answerMap = new HashMap<>();
        answers.forEach((TestSessionAnswerEntity answer) -> answerMap.put(answer.getId(), answer));
        return answerMap;
    }

}
