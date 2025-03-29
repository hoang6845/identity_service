package com.hoang.indentity_service.dto.request;

import com.hoang.indentity_service.validator.AgeOver18;
import com.hoang.indentity_service.validator.Dob;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE) //nếu không modifier thì tu hieu la private
public class UserCreationRequest {
    @NotNull(message = "USERNAME_INVALID")
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    String firstName;
    String lastName;
//    @AgeOver18
    @Dob(min = 18, max = 100, message = "DOB_INVALID") //18 den 100 tuoi
    @NotNull(message = "DOB_NULL")
    LocalDate birthDate;
    Set<String> codeRoles;


}
