package kz.afm.candidate.test.session.evaluation.result;

import kz.afm.candidate.test.TestEntity;
import kz.afm.candidate.test.session.dto.ConditionalSectioningVariableValueDto;
import kz.afm.candidate.test.session.evaluation.increment.OptionIncrementEntity;
import kz.afm.candidate.test.session.evaluation.increment.OptionIncrementService;
import kz.afm.candidate.test.session.evaluation.scale.ScaleEntity;
import kz.afm.candidate.test.session.evaluation.scale.ScaleService;
import kz.afm.candidate.test.session.TestSessionEntity;
import kz.afm.candidate.test.session.answer.TestSessionAnswerEntity;
import kz.afm.candidate.test.session.answer.TestSessionAnswerService;
import kz.afm.candidate.test.session.evaluation.section.conditional.variable_value.ConditionalSectioningVariableValueService;
import kz.afm.candidate.test.session.status.TestSessionStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ResultService {

    private final TestSessionAnswerService testSessionAnswerService;
    private final ScaleService scaleService;
    private final OptionIncrementService optionIncrementService;
    private final TestSessionStatusService testSessionStatusService;
    private final ConditionalSectioningVariableValueService conditionalSectioningVariableValueService;

    private final ResultRepository resultRepository;

    public List<ResultEntity> getByTestSession(TestSessionEntity testSession) {
        return this.resultRepository.findAllByTestSession(testSession);
    }

    public void deleteByTestSession(TestSessionEntity testSession) {
        this.resultRepository.deleteByTestSession(testSession);
    }

    public void evaluate(TestSessionEntity testSession, List<ConditionalSectioningVariableValueDto> varValues) {
        this.conditionalSectioningVariableValueService.create(testSession, varValues);
        List<TestSessionAnswerEntity> answers = this.testSessionAnswerService.getAllByTestSession(testSession);
        final Map<Long, ResultEntity> results = this.createResultMapFor(testSession);
        this.updateResults(results, answers);
    }

    private Map<Long, ResultEntity> createResultMapFor(TestSessionEntity testSession) {
        final List<ScaleEntity> scales = this.scaleService.getAllByTestSession(testSession);
        final Map<Long, ResultEntity> scaleIdToResultMap = new HashMap<>();

        for (ScaleEntity scale : scales) {
            final ResultEntity result = new ResultEntity(testSession, scale);
            scaleIdToResultMap.put(scale.id, result);
        }

        return scaleIdToResultMap;
    }

    private void updateResults(Map<Long, ResultEntity> results, List<TestSessionAnswerEntity> answers) {
        answers.forEach((TestSessionAnswerEntity answer) -> {
            final OptionIncrementEntity increment = this.optionIncrementService.getByOption(answer.getOption());

            final long incrementScaleId = increment.scale.id;
            ResultEntity result = results.get(incrementScaleId);
            result.score += this.pointsToAdd(answer, increment);
            results.replace(incrementScaleId, result);
        });

        this.resultRepository.saveAll(results.values());
    }

    private int pointsToAdd(TestSessionAnswerEntity answer, OptionIncrementEntity optionIncrement) {
        final TestEntity answerTest = this.testSessionAnswerService.getTestFrom(answer);
        if (this.testSessionStatusService.isPointDistribution(answerTest)) {
            return Integer.parseInt(answer.getAnswer());
        } else {
            return optionIncrement.increment;
        }
    }

    public String getType(TestEntity test) {
        if (!test.getType().isAutomaticallyEvaluated()) return "assessment";

        final List<ScaleEntity> scales = this.scaleService.getAllByTest(test);
        if (scales.size() > 1) {
            return "multi-scale";
        } else {
            return "single-scale";
        }
    }

}
