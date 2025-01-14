package kz.afm.candidate.reference.region;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public List<RegionEntity> getAll() {
        return this.regionRepository.findAll();
    }

}
