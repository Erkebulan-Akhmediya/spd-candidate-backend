package kz.afm.candidate.reference.recruited_method;

import kz.afm.candidate.candidate.dto.CandidateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class RecruitedMethodService {

    private final RecruitedMethodRepository recruitedMethodRepository;

    public RecruitedMethodEntity getUsing(CandidateRequest candidateDto) {
        return this.getById(candidateDto.recruitedMethodId);
    }

    public RecruitedMethodEntity getById(int id) throws NoSuchElementException {
        return this.recruitedMethodRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Где нашли кандидата не найдено"));
    }

    public List<RecruitedMethodEntity> getAll(boolean notEmpty) throws NoSuchElementException {
        final List<RecruitedMethodEntity> recruitedMethods = this.recruitedMethodRepository.findAll();
        if (!notEmpty || !recruitedMethods.isEmpty()) return recruitedMethods;
        throw new NoSuchElementException("Где нашли кандидата не найдено");
    }

}
