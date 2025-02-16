package kz.afm.candidate.hr;

import kz.afm.candidate.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HrRepository extends JpaRepository<HrEntity, Long> {
    Optional<HrEntity> findByUser(UserEntity user);
}
