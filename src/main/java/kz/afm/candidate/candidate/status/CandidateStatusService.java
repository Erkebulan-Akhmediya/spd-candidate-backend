package kz.afm.candidate.candidate.status;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CandidateStatusService {

    private final CandidateStatusRepository candidateStatusRepository;

    public void createIfNotExists(final int id, final String nameRus, final String nameKaz) {
        if (this.candidateStatusRepository.findById(id).isPresent()) return;
        this.candidateStatusRepository.save(new CandidateStatusEntity(id, nameRus, nameKaz));
    }

    public List<CandidateStatusEntity> getAll() {
        return this.candidateStatusRepository.findAll();
    }

}
