package kz.afm.candidate.test.question;

import kz.afm.candidate.test.variant.VariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    long countByVariant(VariantEntity variant);
}
