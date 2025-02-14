package kz.afm.candidate.test.session.dto;

import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.test.TestEntity;
import kz.afm.candidate.test.session.TestSessionEntity;
import kz.afm.candidate.test.session.status.TestSessionStatusEntity;

public class TestSessionForAssessmentResponse {
    public String candidateFullName;
    public String testNameRus;
    public String testNameKaz;
    public int statusId;
    public String statusNameRus;
    public String statusNameKaz;

    public TestSessionForAssessmentResponse(TestSessionEntity testSession) {
        CandidateEntity candidate = testSession.getCandidate();
        String candidateFirstName = candidate.getFirstName();
        String candidateLastName = candidate.getLastName();
        String candidateMiddleName = candidate.getMiddleName();
        this.candidateFullName = candidateLastName + " " + candidateFirstName + " " + candidateMiddleName;

        TestEntity test = testSession.getVariant().getTest();
        this.testNameRus = test.getNameRus();
        this.testNameKaz = test.getNameKaz();

        final int checkedTestSessionStatusId = 3;
        TestSessionStatusEntity status = testSession.getStatus();
        this.statusId = status.getId();
        this.statusNameRus = this.statusId == checkedTestSessionStatusId ? status.getNameRus() : "Не проверено";
        this.statusNameKaz = this.statusId == checkedTestSessionStatusId ? status.getNameKaz() : "Тексерілмеген";
    }
}
