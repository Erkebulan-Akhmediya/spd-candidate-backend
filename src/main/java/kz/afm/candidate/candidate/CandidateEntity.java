package kz.afm.candidate.candidate;

import jakarta.persistence.*;
import kz.afm.candidate.candidate.area_of_activity.AreaOfActivityEntity;
import kz.afm.candidate.candidate.status.CandidateStatusEntity;
import kz.afm.candidate.reference.driver_license.DriverLicenseEntity;
import kz.afm.candidate.reference.nationality.NationalityEntity;
import kz.afm.candidate.reference.recruited_method.RecruitedMethodEntity;
import kz.afm.candidate.reference.region.RegionEntity;
import kz.afm.candidate.user.UserEntity;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "candidate")
public class CandidateEntity {

    @Id
    private String identificationNumber;

    private String lastName;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private Date birthDate;

    private String birthPlace;

    @ManyToOne
    @JoinColumn(name = "testing_region_id", nullable = false)
    private RegionEntity testingRegion;

    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "nationality_code", referencedColumnName = "code")
    private NationalityEntity nationality;

    @ManyToMany
    @JoinTable(
            name = "candidate_driver_license_rel",
            joinColumns = @JoinColumn(name = "candidate_identification_number"),
            inverseJoinColumns = @JoinColumn(name = "driver_license_code")
    )
    private Set<DriverLicenseEntity> driverLicenses;

    @Column(length = 1000)
    private String sport;

    @Column(length = 10000)
    private String additionalData;

    @ManyToOne
    @JoinColumn(name = "recruited_method_id", nullable = false)
    private RecruitedMethodEntity recruitedMethod;

    @Column(length = 1000)
    private String recruitedMethodComment;

    @Column(length = 10000)
    private String securityCheckResult;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private CandidateStatusEntity status;

    @Builder.Default
    private Date createDate = new Date();

    @ManyToOne
    @JoinColumn(name = "area_of_activity", referencedColumnName = "name")
    private AreaOfActivityEntity areaOfActivity;

    private String photoFileName;

}
