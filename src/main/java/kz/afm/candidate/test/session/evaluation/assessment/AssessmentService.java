package kz.afm.candidate.test.session.evaluation.assessment;

import kz.afm.candidate.test.session.TestSessionEntity;
import kz.afm.candidate.test.session.answer.TestSessionAnswerEntity;
import kz.afm.candidate.test.session.answer.TestSessionAnswerService;
import kz.afm.candidate.test.session.dto.TestSessionAnswerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AssessmentService {

    private final TestSessionAnswerService testSessionAnswerService;
    private final AssessmentRepository assessmentRepository;

    public void save(TestSessionEntity testSession, List<TestSessionAnswerDto> assessedAnswersDto) {
        final Map<Long, TestSessionAnswerEntity> answers = this.testSessionAnswerService.getMapOfAllByTestSession(testSession);
        final List<AssessmentEntity> assessments = assessedAnswersDto.stream()
                .map(
                        (TestSessionAnswerDto answerDto) -> {
                            final TestSessionAnswerEntity answer = answers.get(answerDto.id);
                            return new AssessmentEntity(answer, answerDto.assessment);
                        }
                )
                .toList();
        this.assessmentRepository.saveAll(assessments);
    }

    public List<AssessmentEntity> getByTestSession(TestSessionEntity testSession) {
        return this.assessmentRepository.findAllByTestSessionAnswer_TestSession(testSession);
    }

}
