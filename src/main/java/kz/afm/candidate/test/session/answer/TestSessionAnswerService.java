package kz.afm.candidate.test.session.answer;

import jakarta.transaction.Transactional;
import kz.afm.candidate.test.session.TestSessionEntity;
import kz.afm.candidate.test.session.dto.TestSessionAnswerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TestSessionAnswerService {

    private final TestSessionAnswerFactoryService testSessionAnswerFactoryService;
    private final TestSessionAnswerRepository testSessionAnswerRepository;

    @Transactional
    public void save(TestSessionEntity testSession, List<TestSessionAnswerRequest> answerDtoList) {
        List<TestSessionAnswerEntity> answers = new LinkedList<>();

        answerDtoList.forEach((TestSessionAnswerRequest answerDto) -> {
            final List<TestSessionAnswerEntity> answersFromDto = this.testSessionAnswerFactoryService.create(testSession, answerDto);
            answers.addAll(answersFromDto);
        });

        this.testSessionAnswerRepository.saveAll(answers);
    }

    public List<TestSessionAnswerEntity> getAllByTestSessionId(TestSessionEntity testSession) {
        return this.testSessionAnswerRepository.findAllByTestSession(testSession);
    }

}
