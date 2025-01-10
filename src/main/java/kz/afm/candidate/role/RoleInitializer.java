package kz.afm.candidate.role;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Order(1)
@Component
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (this.roleRepository.findById("admin").isEmpty()) {
            final RoleEntity role = RoleEntity.builder()
                    .code("admin")
                    .nameRus("Администратор")
                    .nameKaz("Әкімші")
                    .build();
            this.roleRepository.save(role);
        }

        if (this.roleRepository.findById("security").isEmpty()) {
            final RoleEntity role = RoleEntity.builder()
                    .code("security")
                    .nameRus("СБ")
                    .nameKaz("ЖҚ")
                    .build();
            this.roleRepository.save(role);
        }

        if (this.roleRepository.findById("hr").isEmpty()) {
            final RoleEntity role = RoleEntity.builder()
                    .code("hr")
                    .nameRus("Кадровик")
                    .nameKaz("Кадр қызметкері")
                    .build();
            this.roleRepository.save(role);
        }

        if (this.roleRepository.findById("candidate").isEmpty()) {
            final RoleEntity role = RoleEntity.builder()
                    .code("candidate")
                    .nameRus("Кандидат")
                    .nameKaz("Үміткер")
                    .build();
            this.roleRepository.save(role);
        }

    }

}
