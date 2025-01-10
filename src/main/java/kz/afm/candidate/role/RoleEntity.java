package kz.afm.candidate.role;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    private String code;

    @Column(name = "name_rus", nullable = false)
    private String nameRus;

    @Column(name = "name_kaz", nullable = false)
    private String nameKaz;

}
