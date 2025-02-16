package kz.afm.candidate.reference.region;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<RegionEntity, Integer> {
    Optional<RegionEntity> findById(int id);
}
