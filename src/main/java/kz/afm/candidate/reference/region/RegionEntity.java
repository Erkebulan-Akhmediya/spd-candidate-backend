package kz.afm.candidate.reference.region;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "region", schema = "reference")
public class RegionEntity {

    @Id
    public int id;

    public String nameRus;

    public String nameKaz;

}
