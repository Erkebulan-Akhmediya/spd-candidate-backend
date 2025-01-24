package kz.afm.candidate.test.session;

import jakarta.persistence.*;
import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.test.session.status.TestSessionStatusEntity;
import kz.afm.candidate.test.variant.VariantEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "test_session")
public class TestSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "test_variant_id", nullable = false)
    private VariantEntity variant;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private CandidateEntity candidate;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private TestSessionStatusEntity status;

    @Column(nullable = false)
    private Date startDate = new Date();

    private Date endDate;

    public TestSessionEntity(VariantEntity variant, CandidateEntity candidate, TestSessionStatusEntity status) {
        this.variant = variant;
        this.candidate = candidate;
        this.status = status;
        this.startDate = new Date();
    }

}
