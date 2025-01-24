package kz.afm.candidate.test;

import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {
    List<TestEntity> findAllByAreaOfActivitiesContaining(Set<AreaOfActivityEntity> areaOfActivities);

    List<TestEntity> findAllByAreaOfActivitiesContaining(Set<AreaOfActivityEntity> areaOfActivities, Pageable pageable);
}
