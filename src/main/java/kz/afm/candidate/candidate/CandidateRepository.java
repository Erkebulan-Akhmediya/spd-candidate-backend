package kz.afm.candidate.candidate;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateEntity, String> {

    List<CandidateEntity> findByStatus_IdAndTestingRegion_Id(int statusId, int testingRegionId, Pageable pageable);

    List<CandidateEntity> findByStatus_Id(int statusId, Pageable pageable);

    long countByStatus_Id(int statusId);

    long countByStatus_IdAndTestingRegion_Id(int statusId, int testingRegionId);
}
