package kz.afm.candidate.reference.recruited_method;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitedMethodRepository extends JpaRepository<RecruitedMethodEntity, Integer> {
}
