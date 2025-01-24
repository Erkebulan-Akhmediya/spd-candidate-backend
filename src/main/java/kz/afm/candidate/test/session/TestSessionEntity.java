package kz.afm.candidate.test.session;

import jakarta.persistence.*;
import kz.afm.candidate.candidate.CandidateEntity;
import kz.afm.candidate.test.TestEntity;
import kz.afm.candidate.test.session.status.TestSessionStatusEntity;
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
    @JoinColumn(name = "test_id", nullable = false)
    private TestEntity test;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private CandidateEntity candidate;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private TestSessionStatusEntity status;

    @Column(nullable = false)
    private Date startDate = new Date();

    private Date endDate;

    public TestSessionEntity(TestEntity test, CandidateEntity candidate, TestSessionStatusEntity status) {
        this.test = test;
        this.candidate = candidate;
        this.status = status;
        this.startDate = new Date();
    }

}
