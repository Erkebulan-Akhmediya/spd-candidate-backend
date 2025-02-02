package kz.afm.candidate.auth.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String areaOfActivity;
}
