package kz.afm.candidate.reference.language.level;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface LanguageLevelRepository extends JpaRepository<LanguageLevelEntity, String> {
    List<LanguageLevelEntity> findAllByCodeIn(Collection<String> codes);
}
