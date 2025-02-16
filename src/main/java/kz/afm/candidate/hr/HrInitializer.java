package kz.afm.candidate.hr;

import kz.afm.candidate.reference.region.RegionEntity;
import kz.afm.candidate.reference.region.RegionRepository;
import kz.afm.candidate.role.RoleEntity;
import kz.afm.candidate.role.RoleRepository;
import kz.afm.candidate.user.UserEntity;
import kz.afm.candidate.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@RequiredArgsConstructor
@Order(4)
@Component
public class HrInitializer implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final HrRepository hrRepository;
    private final UserRepository userRepository;
    private final RegionRepository regionRepository;

    @Override
    public void run(String... args) throws Exception {

        final RoleEntity role = this.roleRepository.findById("hr").orElseThrow();
        final Set<RoleEntity> roles = new HashSet<>() {{
            add(role);
        }};

        List<HrInitializerItem> items = new LinkedList<>() {{
            add(new HrInitializerItem("astana", 1));
            add(new HrInitializerItem("almaty", 2));
            add(new HrInitializerItem("shymkent", 3));
            add(new HrInitializerItem("pavlodar", 4));
            add(new HrInitializerItem("sko", 5));
            add(new HrInitializerItem("kostanai", 6));
            add(new HrInitializerItem("akmola", 7));
            add(new HrInitializerItem("abai", 8));
            add(new HrInitializerItem("vko", 9));
            add(new HrInitializerItem("karaganda", 10));
            add(new HrInitializerItem("ulytau", 11));
            add(new HrInitializerItem("almaty_o", 12));
            add(new HrInitializerItem("zhetisu", 13));
            add(new HrInitializerItem("zhambyl", 14));
            add(new HrInitializerItem("turkistan", 15));
            add(new HrInitializerItem("kyzylorda", 16));
            add(new HrInitializerItem("zko", 17));
            add(new HrInitializerItem("atyrau", 18));
            add(new HrInitializerItem("aktobe", 19));
            add(new HrInitializerItem("mangystau", 20));
        }};

        for (HrInitializerItem item : items) {
            if (this.userRepository.existsByUsername(item.usernamePassword)) continue;
            RegionEntity region = this.regionRepository.findById(item.regionId).orElseThrow();

            UserEntity user = UserEntity.builder()
                    .username(item.usernamePassword)
                    .password(this.passwordEncoder.encode(item.usernamePassword))
                    .roles(roles)
                    .build();
            UserEntity newUser = this.userRepository.save(user);
            HrEntity hr = new HrEntity(newUser, region);
            this.hrRepository.save(hr);
        }

    }

}
