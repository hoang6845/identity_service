package com.hoang.indentity_service.dto.response;

import com.hoang.indentity_service.entity.Roles;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserResponse {
    String id;
    String username;
    String firstName;
    String lastName;
    LocalDate birthDate;
    String fullName;
    Set<RolesResponse> roles;
}
