package kz.afm.candidate.candidate.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class CreateCandidateRequest {

    @NotNull(message = "ИИН обязателен")
    @NotEmpty(message = "ИИН не должен быть пуст")
    private String identificationNumber;

    @NotNull(message = "фамилия обязательна")
    @NotEmpty(message = "фамилия не должна быть пуста")
    private String lastName;

    @NotNull(message = "имя обязательно")
    @NotEmpty(message = "имя не должено быть пустым")
    private String firstName;

    private String middleName;

    @NotNull(message = "дата рождения обязательна")
    @NotEmpty(message = "дата рождения не должна быть пуста")
    private Date birthDate;

    @NotNull(message = "место рождения обязательно")
    @NotEmpty(message = "место рождения не должно быть пусто")
    private String birthPlace;

    @NotNull(message = "номер телефона обязателен")
    @NotEmpty(message = "номер телефона не должен быть пуст")
    private String phoneNumber;

    @NotNull(message = "национальность обязательна")
    @NotEmpty(message = "национальность не должна быть пустой")
    private int nationalityCode;

    @NotNull(message = "образование обязательно")
    @NotEmpty(message = "образование не должно быть пустым")
    private String education;

    private Set<String> languageCodes;

    private Set<String> driverLicenseCodes;

    @NotNull(message = "отношение к спорту обязательно")
    @NotEmpty(message = "отношение к спорту не должно быть пустым")
    private String sport;

    private String additionalData;

    @NotNull(message = "место откуда подобран кандидат обязательно")
    @NotEmpty(message = "место откуда подобран кандидат не должно быть пустым")
    private int recruitedMethodId;

    private String recruitedMethodComment;

    @NotNull(message = "результат проверки ВБ обязателен")
    @NotEmpty(message = "результат проверки ВБ не должен быть пуст")
    private String securityCheckResult;

    private Set<ExperienceRequest> experiences;

    @NotNull(message = "имя пользователя обязательно")
    @NotEmpty(message = "имя пользователя не должно быть пустым")
    private String username;

    @NotNull(message = "пароль обязателен")
    @NotEmpty(message = "пароль не должен быть пуст")
    private String password;
}
