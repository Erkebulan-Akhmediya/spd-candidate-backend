package kz.afm.candidate.candidate.education.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationTypeRepository extends JpaRepository<EducationTypeEntity,Integer> {
}
