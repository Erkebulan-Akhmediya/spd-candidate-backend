package kz.afm.candidate.test.evaluation.scale;

import kz.afm.candidate.test.TestEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScaleRepository extends JpaRepository<ScaleEntity, Long> {
    List<ScaleEntity> findAllByTest(TestEntity test, Sort sort);
}
