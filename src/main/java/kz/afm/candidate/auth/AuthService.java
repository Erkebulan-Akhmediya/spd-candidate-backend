package kz.afm.candidate.auth;

import kz.afm.candidate.role.RoleEntity;
import kz.afm.candidate.user.UserEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    public Map<String, Object> userToExtraClaims(UserEntity user) {
        Map<String, Object> result = new HashMap<>();

        result.put("id", user.getId());
        result.put("username", user.getUsername());
        result.put("password", user.getPassword());
        result.put("roles", user.getRoles().stream().map(
                (RoleEntity role) -> new HashMap<String, Object>() {{
                    put("code", role.getCode());
                    put("nameRus", role.getNameRus());
                    put("nameKaz", role.getNameKaz());
                }}
        ).toArray());

        return result;
    }

}
