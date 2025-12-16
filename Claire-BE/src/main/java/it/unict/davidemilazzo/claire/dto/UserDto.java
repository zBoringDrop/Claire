package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unict.davidemilazzo.claire.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;

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

    private Role role;

    @JsonCreator
    public UserDto(@JsonProperty("id") Long id,
                   @JsonProperty("nickname") String nickname,
                   @JsonProperty("name") String name,
                   @JsonProperty("surname") String surname,
                   @JsonProperty("email") String email,
                   @JsonProperty("role") Role role) {
        this.id = id;
        this.nickname = nickname;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
    }
}
