package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import it.unict.davidemilazzo.claire.validation.ValidationCode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class UserLoginDto {

    @NotBlank(message = "EMAIL_BLANK")
    @Email(message = "EMAIL_INVALID")
    @Size(min = 3, max = 70, message = "EMAIL_SIZE")
    private String email;

    @NotBlank(message = "PASSWORD_BLANK")
    @Size(min = 3, max = 30, message = "PASSWORD_SIZE")
    private String password;

    @JsonCreator
    public UserLoginDto(@JsonProperty("email") String email,
                        @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
    }
}