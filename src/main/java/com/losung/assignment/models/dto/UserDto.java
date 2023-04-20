package com.losung.assignment.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(message = "username is null")
    private String userName;

    @NotNull(message = "password is null")
    private String password;
}
