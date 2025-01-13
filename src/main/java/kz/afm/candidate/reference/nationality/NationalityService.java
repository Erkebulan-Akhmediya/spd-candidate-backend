package kz.afm.candidate.reference.nationality;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class NationalityService {

    private final NationalityRepository nationalityRepository;

    public NationalityEntity getById(int id) throws NoSuchElementException {
        return this.nationalityRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Национальность не найдена"));
    }

}
