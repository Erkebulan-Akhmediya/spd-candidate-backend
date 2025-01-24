package kz.afm.candidate.auth.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private String error;
    private String token;
    private String areaOfActivity;
}
