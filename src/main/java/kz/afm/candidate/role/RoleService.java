package kz.afm.candidate.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleEntity getByCode(String code) throws NoSuchElementException {
        return this.roleRepository.findById(code).orElseThrow(
                () -> new NoSuchElementException("Роль с кодом  " + code + " не найдена")
        );
    }

}
