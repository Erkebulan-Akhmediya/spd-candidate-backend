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

    public CandidateStatusEntity getById(final int id) throws NoSuchElementException {
        return this.candidateStatusRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Статус с ID: " + id + " не найден")
        );
    }

}
