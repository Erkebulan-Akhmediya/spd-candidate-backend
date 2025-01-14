package kz.afm.candidate.reference.nationality;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nationality", schema = "reference")
public class NationalityEntity {

    @Id
    private int code;

    @Column(name = "name_kaz", nullable = false)
    private String nameKaz;

    @Column(name = "name_rus", nullable = false)
    private String nameRus;

}
