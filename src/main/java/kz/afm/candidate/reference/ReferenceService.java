package kz.afm.candidate.reference;

import kz.afm.candidate.reference.region.RegionEntity;
import kz.afm.candidate.reference.region.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReferenceService {

    private final RegionRepository regionRepository;

    public List<RegionEntity> getAllRegions() {
        return this.regionRepository.findAll();
    }

}
