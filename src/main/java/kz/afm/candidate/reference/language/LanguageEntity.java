package kz.afm.candidate.reference.language;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "language", schema = "reference")
public class LanguageEntity {

    @Id
    public String code;

    @Column(name = "name_rus", nullable = false)
    public String nameRus;

    @Column(name = "name_kaz", nullable = false)
    public String nameKaz;

}
