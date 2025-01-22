package kz.afm.candidate.test.option;

import jakarta.persistence.*;
import kz.afm.candidate.test.question.QuestionEntity;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "option")
public class OptionEntity {

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

    private Boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    public OptionEntity(
            boolean withFile,
            String fileName,
            String nameRus,
            String nameKaz,
            Boolean isCorrect,
            QuestionEntity question
    ) {
        this.withFile = withFile;
        this.fileName = fileName;
        this.nameRus = nameRus;
        this.nameKaz = nameKaz;
        this.isCorrect = isCorrect;
        this.question = question;
    }

}
