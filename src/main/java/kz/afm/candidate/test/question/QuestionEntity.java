package kz.afm.candidate.test.question;

import jakarta.persistence.*;
import kz.afm.candidate.test.question.type.QuestionTypeEntity;
import kz.afm.candidate.test.variant.VariantEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "question")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private boolean withFile;

    private String fileName;

    @Column(nullable = false)
    private String nameRus;

    @Column(nullable = false)
    private String nameKaz;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private QuestionTypeEntity type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", nullable = false)
    private VariantEntity variant;

    public QuestionEntity(
            boolean withFile,
            String fileName,
            String nameRus,
            String nameKaz,
            QuestionTypeEntity type,
            VariantEntity variant
    ) {
        this.withFile = withFile;
        this.fileName = fileName;
        this.nameRus = nameRus;
        this.nameKaz = nameKaz;
        this.type = type;
        this.variant = variant;
    }

}
