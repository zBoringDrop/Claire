package it.unict.davidemilazzo.claire.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AuthenticationResponseDto {
    private final String jwtToken;

    @JsonCreator
    public AuthenticationResponseDto(@JsonProperty("jwt_token") String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
