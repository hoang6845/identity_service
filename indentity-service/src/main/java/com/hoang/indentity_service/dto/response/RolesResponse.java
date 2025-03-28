package com.hoang.indentity_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolesResponse {
    private String codeRole;
    private String nameRole;
    Set<PermissionsResponse> permissions;
}
