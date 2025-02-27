package kz.afm.candidate.reference.language;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface LanguageRepository extends JpaRepository<LanguageEntity, String> {
    List<LanguageEntity> findAllByCodeIn(Collection<String> codes);
}
