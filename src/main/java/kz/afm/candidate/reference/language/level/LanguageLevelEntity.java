package kz.afm.candidate.reference.language.level;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "language_level", schema = "reference")
public class LanguageLevelEntity {

    @Id
    @Column(length = 5)
    public String code;

    public String nameRus;

    public String nameKaz;

}
