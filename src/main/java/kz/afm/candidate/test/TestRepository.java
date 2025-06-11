package kz.afm.candidate.test;

import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {
    List<TestEntity> findAllByAreaOfActivitiesContainingAndDeleted(Set<AreaOfActivityEntity> areaOfActivities, boolean deleted);

    List<TestEntity> findAllByNameRus(String nameRus);

    List<TestEntity> findAllByDeletedIsFalse();

    Page<TestEntity> findAllByDeletedIsFalse(Pageable pageable);

    long countAllByDeleted(boolean deleted);
}
