package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unict.davidemilazzo.claire.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegistrationDto {
    @NotBlank(message = "NICKNAME_BLANK")
    @Size(min = 3, max = 30, message = "NICKNAME_SIZE")
    private String nickname;

    @NotBlank(message = "NAME_BLANK")
    @Size(min = 3, max = 30, message = "NAME_SIZE")
    private String name;

    @NotBlank(message = "SURNAME_BLANK")
    @Size(min = 3, max = 30, message = "SURNAME_SIZE")
    private String surname;

    @NotBlank(message = "EMAIL_BLANK")
    @Email(message = "EMAIL_INVALID")
    @Size(min = 3, max = 70, message = "EMAIL_SIZE")
    private String email;

    @NotBlank(message = "PASSWORD_BLANK")
    @Size(min = 3, max = 30, message = "PASSWORD_SIZE")
    private String password;

    private Role role;

    @JsonCreator
    public UserRegistrationDto(@JsonProperty("nickname") String nickname,
                               @JsonProperty("name") String name,
                               @JsonProperty("surname") String surname,
                               @JsonProperty("email") String email,
                               @JsonProperty("password") String password,
                               @JsonProperty("role") Role role) {
        this.nickname = nickname;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
