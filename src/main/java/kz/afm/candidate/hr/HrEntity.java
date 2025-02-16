package kz.afm.candidate.hr;

import jakarta.persistence.*;
import kz.afm.candidate.reference.region.RegionEntity;
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
@Table(name = "hr")
public class HrEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "region_id", referencedColumnName = "id", nullable = false)
    private RegionEntity region;

    public HrEntity(UserEntity user, RegionEntity region) {
        this.user = user;
        this.region = region;
    }

}
