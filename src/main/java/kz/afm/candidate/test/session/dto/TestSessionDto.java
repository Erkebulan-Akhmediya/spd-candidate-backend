package kz.afm.candidate.test.session.dto;

import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.test.TestEntity;
import kz.afm.candidate.test.session.TestSessionEntity;
import kz.afm.candidate.test.session.answer.TestSessionAnswerEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class TestSessionDto {
    public long id;
    public String candidateFullName;
    public String testNameRus;
    public String testNameKaz;
    public List<TestSessionAnswerDto> answers;

    public TestSessionDto(TestSessionEntity testSession) {
        this(testSession, new LinkedList<>());
    }

    public TestSessionDto(TestSessionEntity testSession, List<TestSessionAnswerEntity> answers) {
        this.id = testSession.getId();

        CandidateEntity candidate = testSession.getCandidate();
        String candidateFirstName = candidate.getFirstName();
        String candidateLastName = candidate.getLastName();
        String candidateMiddleName = candidate.getMiddleName();
        this.candidateFullName = candidateLastName + " " + candidateFirstName + " " + candidateMiddleName;

        TestEntity test = testSession.getVariant().getTest();
        this.testNameRus = test.getNameRus();
        this.testNameKaz = test.getNameKaz();

        this.answers = answers.stream()
                .map(TestSessionAnswerDto::new)
                .toList();
    }

}
