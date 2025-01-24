package kz.afm.candidate.test.session.status;

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
@Table(name = "test_session_status")
public class TestSessionStatusEntity {

    @Id
    private int id;

    private String nameRus;

    private String nameKaz;

}
