package kz.afm.candidate.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotNull(message = "Имя пользователя обязательно")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @NotNull(message = "Пароль обязателен")
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

}
