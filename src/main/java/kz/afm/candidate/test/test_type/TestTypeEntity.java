package kz.afm.candidate.test.test_type;

import jakarta.persistence.Column;
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
@Table(name = "test_type")
public class TestTypeEntity {

    @Id
    private int id;

    @Column(length = 50, nullable = false)
    private String nameRus;

    @Column(length = 50, nullable = false)
    private String nameKaz;

}
