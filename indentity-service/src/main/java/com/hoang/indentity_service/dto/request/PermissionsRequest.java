package com.hoang.indentity_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PermissionsRequest {
    private String codePermission;
    private String namePermission;
    private String description;
}
