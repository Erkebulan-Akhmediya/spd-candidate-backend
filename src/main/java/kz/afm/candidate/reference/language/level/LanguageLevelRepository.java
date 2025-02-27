package kz.afm.candidate.reference.language.level;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageLevelRepository extends JpaRepository<LanguageLevelEntity, String> {
}
