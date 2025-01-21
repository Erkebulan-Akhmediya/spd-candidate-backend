package kz.afm.candidate.test.option;

import jakarta.persistence.*;
import kz.afm.candidate.test.question.QuestionEntity;
import lombok.*;

import java.util.UUID;

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

    private UUID fileId;

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
            UUID fileId,
            String nameRus,
            String nameKaz,
            Boolean isCorrect,
            QuestionEntity question
    ) {
        this.withFile = withFile;
        this.fileId = fileId;
        this.nameRus = nameRus;
        this.nameKaz = nameKaz;
        this.isCorrect = isCorrect;
        this.question = question;
    }

}
