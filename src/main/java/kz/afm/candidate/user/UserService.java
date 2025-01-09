package kz.afm.candidate.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserEntity getByUsername(String username) {
        return this.userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("Пользователь не найден")
        );
    }

    public UserDetailsService getUserDetailsService() {
        return this::getByUsername;
    }

}
