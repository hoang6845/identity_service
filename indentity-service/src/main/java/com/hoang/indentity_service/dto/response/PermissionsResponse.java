package com.hoang.indentity_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionsResponse {
    private String codePermission;
    private String namePermission;
    private String description;
}
