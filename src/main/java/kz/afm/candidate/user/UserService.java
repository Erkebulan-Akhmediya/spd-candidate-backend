package kz.afm.candidate.user;

import kz.afm.candidate.role.RoleEntity;
import kz.afm.candidate.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleService roleService;

    public UserEntity getByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("Пользователь не найден")
        );
    }

    public UserDetailsService getUserDetailsService() {
        return this::getByUsername;
    }

    public UserEntity createForCandidate(String username, String password) throws NoSuchElementException {
        final RoleEntity role = this.roleService.getByCode("candidate");
        final UserEntity user = new UserEntity(
                username,
                this.passwordEncoder.encode(password),
                new HashSet<>(){{ add(role); }}
        );
        return this.userRepository.save(user);
    }

}
