package com.hoang.indentity_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RolesRequest {
    private String codeRole;
    private String nameRole;
    Set<String> codePermissions;
}
