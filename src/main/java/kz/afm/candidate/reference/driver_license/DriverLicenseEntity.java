package kz.afm.candidate.reference.driver_license;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import kz.afm.candidate.candidate.CandidateEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "driver_license", schema = "reference")
public class DriverLicenseEntity {

    @Id
    private String code;

    @ManyToMany(mappedBy = "driverLicenses")
    private Set<CandidateEntity> candidates;

}
