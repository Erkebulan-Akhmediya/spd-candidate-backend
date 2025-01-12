package kz.afm.candidate.reference.region;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "region", schema = "reference")
public class RegionEntity {

    @Id
    private int id;

    private String nameRus;

    private String nameKaz;

}
