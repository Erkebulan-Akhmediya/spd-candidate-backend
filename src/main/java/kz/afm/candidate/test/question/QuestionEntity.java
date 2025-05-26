package kz.afm.candidate.test.question;

import jakarta.persistence.*;
import kz.afm.candidate.test.variant.VariantEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "question")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    @Column(nullable = false)
    public boolean withFile;

    @Column(length = 1000)
    public String fileName;

    @Column(nullable = false, length = 10000)
    public String nameRus;

    @Column(nullable = false, length = 10000)
    public String nameKaz;

    @ColumnDefault("false")
    @Column(nullable = false)
    public boolean isDisappearing;

    @ColumnDefault("0")
    @Column(nullable = false)
    public int timeToDisappear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", nullable = false)
    public VariantEntity variant;

    public QuestionEntity(
            boolean withFile,
            String fileName,
            String nameRus,
            String nameKaz,
            boolean isDisappearing,
            int timeToDisappear,
            VariantEntity variant
    ) {
        this.withFile = withFile;
        this.fileName = fileName;
        this.nameRus = nameRus;
        this.nameKaz = nameKaz;
        this.isDisappearing = isDisappearing;
        this.timeToDisappear = timeToDisappear;
        this.variant = variant;
    }

}
