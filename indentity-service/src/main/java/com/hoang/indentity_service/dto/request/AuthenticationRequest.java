package com.hoang.indentity_service.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @NotNull(message = "USERNAME_INVALID")
    @Size(min =3, message = "USERNAME_INVALID")
    String username;
    @NotNull(message = "PASSWORD_INVALID")
    @Size(min = 3, message = "PASSWORD_INVALID")
    String password;
}
