package kz.afm.candidate.test.evaluation.result;

import kz.afm.candidate.test.evaluation.increment.OptionIncrementEntity;
import kz.afm.candidate.test.evaluation.increment.OptionIncrementService;
import kz.afm.candidate.test.evaluation.scale.ScaleEntity;
import kz.afm.candidate.test.evaluation.scale.ScaleService;
import kz.afm.candidate.test.session.TestSessionEntity;
import kz.afm.candidate.test.session.TestSessionService;
import kz.afm.candidate.test.session.answer.TestSessionAnswerEntity;
import kz.afm.candidate.test.session.answer.TestSessionAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class ResultService {

    private final TestSessionService testSessionService;
    private final TestSessionAnswerService testSessionAnswerService;
    private final ScaleService scaleService;
    private final OptionIncrementService optionIncrementService;
    private final ResultRepository resultRepository;

    public Object get(long testSessionId) {
        final TestSessionEntity testSession = this.testSessionService.getById(testSessionId);
        if (this.automaticallyEvaluated(testSession)) {
            final boolean resultExists = this.resultRepository.findAllByTestSession(testSession).isEmpty();
            if (!resultExists) {
                this.evaluate(testSession);
            }
        }
        return null;
    }

    private boolean automaticallyEvaluated(TestSessionEntity testSession) {
        final int noCorrectTestTypeId = 2;
        final int oneCorrectTestTypeId = 3;
        final int multipleCorrectTestTypeId = 4;
        final int pointDistributionTestTypeId = 5;
        final Set<Integer> automaticallyEvaluatedTestTypeIds = Set.of(
                noCorrectTestTypeId,
                oneCorrectTestTypeId,
                multipleCorrectTestTypeId,
                pointDistributionTestTypeId
        );

        final int testTypeId = testSession.getVariant().getTest().getType().getId();
        return automaticallyEvaluatedTestTypeIds.contains(testTypeId);
    }

    public void evaluate(TestSessionEntity testSession) {
        List<TestSessionAnswerEntity> answers = this.testSessionAnswerService.getAllByTestSessionId(testSession);
        if (this.answersWithNoOptions(answers)) return;
        final Map<Long, ResultEntity> results = this.createResultMapFor(testSession);
        this.updateResults(results, answers);
    }

    private boolean answersWithNoOptions(List<TestSessionAnswerEntity> answers) {
        return !answers.stream().allMatch((TestSessionAnswerEntity answer) -> answer.getOption() != null);
    }

    private Map<Long, ResultEntity> createResultMapFor(TestSessionEntity testSession) {
        final List<ScaleEntity> scales = this.scaleService.getAllByTestSession(testSession);

        final List<ResultEntity> results = scales.stream()
                .map((ScaleEntity scale) -> new ResultEntity(testSession, scale))
                .toList();

        final Map<Long, ResultEntity> scaleIdToResultMap = new HashMap<>();

        for (ResultEntity result : results) {
            scaleIdToResultMap.put(result.getScale().getId(), result);
        }

        return scaleIdToResultMap;
    }

    private void updateResults(Map<Long, ResultEntity> results, List<TestSessionAnswerEntity> answers) {
        answers.forEach((TestSessionAnswerEntity answer) -> {
            final OptionIncrementEntity increment = this.optionIncrementService.getByOption(answer.getOption());

            final long incrementScaleId = increment.getScale().getId();
            ResultEntity result = results.get(incrementScaleId);
            result.setScore(result.getScore() + increment.getIncrement());
            results.replace(incrementScaleId, result);
        });

        this.resultRepository.saveAll(results.values());
    }

}
