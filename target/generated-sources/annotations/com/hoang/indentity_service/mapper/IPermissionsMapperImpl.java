package com.hoang.indentity_service.mapper;

import com.hoang.indentity_service.dto.request.PermissionsRequest;
import com.hoang.indentity_service.dto.response.PermissionsResponse;
import com.hoang.indentity_service.entity.Permissions;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class IPermissionsMapperImpl implements IPermissionsMapper {

    @Override
    public PermissionsResponse ToPermissionsResponse(Permissions permissions) {
        if ( permissions == null ) {
            return null;
        }

        PermissionsResponse.PermissionsResponseBuilder permissionsResponse = PermissionsResponse.builder();

        permissionsResponse.codePermission( permissions.getCodePermission() );
        permissionsResponse.namePermission( permissions.getNamePermission() );
        permissionsResponse.description( permissions.getDescription() );

        return permissionsResponse.build();
    }

    @Override
    public Permissions ToPermissions(PermissionsRequest permissionsResponse) {
        if ( permissionsResponse == null ) {
            return null;
        }

        Permissions permissions = new Permissions();

        permissions.setCodePermission( permissionsResponse.getCodePermission() );
        permissions.setNamePermission( permissionsResponse.getNamePermission() );
        permissions.setDescription( permissionsResponse.getDescription() );

        return permissions;
    }
}
