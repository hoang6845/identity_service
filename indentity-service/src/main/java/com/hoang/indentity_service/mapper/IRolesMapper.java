package com.hoang.indentity_service.mapper;

import com.hoang.indentity_service.dto.request.RolesRequest;
import com.hoang.indentity_service.dto.response.RolesResponse;
import com.hoang.indentity_service.entity.Roles;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IRolesMapper {
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "users", ignore = true)
    Roles toRoles(RolesRequest rolesRequest);

    RolesResponse toRolesResponse(Roles roles);
}
