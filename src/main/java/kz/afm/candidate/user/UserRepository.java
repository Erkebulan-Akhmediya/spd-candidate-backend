package kz.afm.candidate.user;

import kz.afm.candidate.role.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findAllByRolesContaining(Set<RoleEntity> roles);

    boolean existsByUsername(String username);
}
