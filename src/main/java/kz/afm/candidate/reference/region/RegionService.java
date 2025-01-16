package kz.afm.candidate.reference.region;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public List<RegionEntity> getAll() {
        return this.regionRepository.findAll();
    }

    public RegionEntity getById(int id) {
        return this.regionRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Не найден реион с ID: " + id)
        );
    }

}
