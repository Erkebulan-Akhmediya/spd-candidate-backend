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
public class CandidateRequest {

    @NotNull(message = "ИИН обязателен")
    @NotEmpty(message = "ИИН не должен быть пуст")
    public String identificationNumber;

    @NotNull(message = "фамилия обязательна")
    @NotEmpty(message = "фамилия не должна быть пуста")
    public String lastName;

    @NotNull(message = "имя обязательно")
    @NotEmpty(message = "имя не должено быть пустым")
    public String firstName;

    public String middleName;

    @NotNull(message = "дата рождения обязательна")
    @NotEmpty(message = "дата рождения не должна быть пуста")
    public Date birthDate;

    @NotNull(message = "место рождения обязательно")
    @NotEmpty(message = "место рождения не должно быть пусто")
    public String birthPlace;

    @NotEmpty(message = "регион тестирования не должен быть пуст")
    public int testingRegionId;

    @NotNull(message = "номер телефона обязателен")
    @NotEmpty(message = "номер телефона не должен быть пуст")
    public String phoneNumber;

    @NotEmpty(message = "национальность не должна быть пустой")
    public int nationalityCode;

    @NotNull(message = "образование обязательно")
    @NotEmpty(message = "образование не должно быть пустым")
    public Set<EducationDto> education;

    public Set<String> languageCodes;

    public Set<String> driverLicenseCodes;

    @NotNull(message = "отношение к спорту обязательно")
    @NotEmpty(message = "отношение к спорту не должно быть пустым")
    public String sport;

    public String additionalData;

    @NotEmpty(message = "место откуда подобран кандидат не должно быть пустым")
    public int recruitedMethodId;

    public String recruitedMethodComment;

    @NotNull(message = "результат проверки ВБ обязателен")
    @NotEmpty(message = "результат проверки ВБ не должен быть пуст")
    public String securityCheckResult;

    public Set<ExperienceDto> experiences;

    @NotNull(message = "имя пользователя обязательно")
    @NotEmpty(message = "имя пользователя не должно быть пустым")
    public String username;

    @NotNull(message = "пароль обязателен")
    @NotEmpty(message = "пароль не должен быть пуст")
    public String password;

    public String photoFileName;
}
