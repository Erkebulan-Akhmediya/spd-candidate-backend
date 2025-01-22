package kz.afm.candidate.candidate.area_of_activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaOfActivityRepository extends JpaRepository<AreaOfActivityEntity, String> {
}
