package kz.afm.candidate.candidate.education.type;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "education_type", schema = "reference")
public class EducationTypeEntity {

    @Id
    public int id;

    public String nameRus;

    public String nameKaz;

}
