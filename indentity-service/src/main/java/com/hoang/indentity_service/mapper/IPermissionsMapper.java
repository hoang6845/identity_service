package com.hoang.indentity_service.mapper;

import com.hoang.indentity_service.dto.request.PermissionsRequest;
import com.hoang.indentity_service.dto.response.PermissionsResponse;
import com.hoang.indentity_service.entity.Permissions;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IPermissionsMapper {
    PermissionsResponse ToPermissionsResponse(Permissions permissions);
    Permissions ToPermissions(PermissionsRequest permissionsResponse);
}
