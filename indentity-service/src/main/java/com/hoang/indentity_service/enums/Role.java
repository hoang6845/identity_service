package com.hoang.indentity_service.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum Role {
    ROLE_USER("USER", "người dùng"),
    ROLE_ADMIN("ADMIN", "quản trị viên");

    private final String codeRole;
    private final String nameRole;
}
