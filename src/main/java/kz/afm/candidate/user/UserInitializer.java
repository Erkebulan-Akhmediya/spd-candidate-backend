package kz.afm.candidate.user;

import kz.afm.candidate.role.RoleEntity;
import kz.afm.candidate.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Order(2)
@Component
public class UserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        final RoleEntity role = this.roleRepository.findById("admin").orElseThrow();
        final Set<RoleEntity> roles = new HashSet<>(){{ add(role); }};
        final boolean isAdminAbsent = this.userRepository.findAllByRolesContaining(roles).isEmpty();

        if (isAdminAbsent) {
            final UserEntity user = UserEntity.builder()
                    .username("admin")
                    .password(this.passwordEncoder.encode("admin"))
                    .roles(roles)
                    .build();
            this.userRepository.save(user);
        }

        final RoleEntity psychoRole = this.roleRepository.findById("psycho").orElseThrow();
        final boolean isPsychoUserExists = this.userRepository.findByUsername("psycho").isPresent();

        if (!isPsychoUserExists) {
            final Set<RoleEntity> psychoRoles = new HashSet<>() {{ add(psychoRole); }};
            final UserEntity psychoUser = UserEntity.builder()
                    .username("psycho")
                    .password(this.passwordEncoder.encode("psycho"))
                    .roles(psychoRoles)
                    .build();
            this.userRepository.save(psychoUser);
        }

    }

}
