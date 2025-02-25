package kz.afm.candidate.candidate.status;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class CandidateStatusService {

    private final CandidateStatusRepository candidateStatusRepository;

    public void createIfNotExists(final int id, final String nameRus, final String nameKaz) {
        if (this.candidateStatusRepository.findById(id).isPresent()) return;
        this.candidateStatusRepository.save(new CandidateStatusEntity(id, nameRus, nameKaz));
    }

    public CandidateStatusEntity getNewCandidateStatus() {
        final int newCandidateStatusId = 1;
        return this.getById(newCandidateStatusId);
    }

    public CandidateStatusEntity getOnSecurityCheckStatus() {
        final int onSecurityCheckStatusId = 2;
        return this.getById(onSecurityCheckStatusId);
    }

    public CandidateStatusEntity getOnApprovalStatus() {
        final int onApprovalStatusId = 3;
        return this.getById(onApprovalStatusId);
    }

    public CandidateStatusEntity getApprovedStatus() {
        final int approvedStatusId = 4;
        return this.getById(approvedStatusId);
    }

    public CandidateStatusEntity getRejectedStatus() {
        final int rejectedStatusId = 5;
        return this.getById(rejectedStatusId);
    }

    public CandidateStatusEntity getById(final int id) throws NoSuchElementException {
        return this.candidateStatusRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Статус с ID: " + id + " не найден")
        );
    }

}
