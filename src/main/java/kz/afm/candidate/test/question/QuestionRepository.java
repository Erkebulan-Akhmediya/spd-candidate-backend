package kz.afm.candidate.test.question;

import kz.afm.candidate.test.variant.VariantEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    List<QuestionEntity> findAllByVariant(VariantEntity variant, Sort sort);

    List<QuestionEntity> findAllByVariantIn(Collection<VariantEntity> variants);

    List<QuestionEntity> findAllByVariant_Id(long variantId);
}
