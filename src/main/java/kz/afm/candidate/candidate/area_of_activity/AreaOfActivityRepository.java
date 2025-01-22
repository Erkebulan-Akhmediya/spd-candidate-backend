package kz.afm.candidate.candidate.area_of_activity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface AreaOfActivityRepository extends JpaRepository<AreaOfActivityEntity, String> {
    List<AreaOfActivityEntity> findAllByNameIn(Collection<String> names);
}
