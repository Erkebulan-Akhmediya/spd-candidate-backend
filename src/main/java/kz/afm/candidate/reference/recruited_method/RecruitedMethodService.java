package kz.afm.candidate.reference.recruited_method;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class RecruitedMethodService {

    private final RecruitedMethodRepository recruitedMethodRepository;

    public RecruitedMethodEntity getById(int id) throws NoSuchElementException {
        return this.recruitedMethodRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Где нашли кандидата не найдено"));
    }

}
