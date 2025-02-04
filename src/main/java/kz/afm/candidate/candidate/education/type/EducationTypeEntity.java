package kz.afm.candidate.candidate.education.type;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "education_type", schema = "reference")
public class EducationTypeEntity {

    @Id
    private int id;

    private String nameRus;

    private String nameKaz;

}
