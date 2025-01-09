package kz.afm.candidate.role;

import jakarta.persistence.*;
import kz.afm.candidate.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    private String code;

    @Column(name = "name_rus", nullable = false)
    private String nameRus;

    @Column(name = "name_kaz", nullable = false)
    private String nameKaz;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;

}
