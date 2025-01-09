package kz.afm.candidate.employee;

import jakarta.persistence.*;
import kz.afm.candidate.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id
    private Long id;

    private String firstName;

    private String lastName;

    private String middleName;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

}
