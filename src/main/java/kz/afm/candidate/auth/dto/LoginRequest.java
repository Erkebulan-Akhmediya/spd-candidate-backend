package kz.afm.candidate.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotNull(message = "Имя пользователя обязательно")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    public String username;

    @NotNull(message = "Пароль обязателен")
    @NotBlank(message = "Пароль не может быть пустым")
    public String password;

}
