package com.hoang.indentity_service.mapper;

import com.hoang.indentity_service.dto.request.RolesRequest;
import com.hoang.indentity_service.dto.response.PermissionsResponse;
import com.hoang.indentity_service.dto.response.RolesResponse;
import com.hoang.indentity_service.entity.Permissions;
import com.hoang.indentity_service.entity.Roles;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class IRolesMapperImpl implements IRolesMapper {

    @Override
    public Roles toRoles(RolesRequest rolesRequest) {
        if ( rolesRequest == null ) {
            return null;
        }

        Roles.RolesBuilder roles = Roles.builder();

        roles.codeRole( rolesRequest.getCodeRole() );
        roles.nameRole( rolesRequest.getNameRole() );

        return roles.build();
    }

    @Override
    public RolesResponse toRolesResponse(Roles roles) {
        if ( roles == null ) {
            return null;
        }

        RolesResponse.RolesResponseBuilder rolesResponse = RolesResponse.builder();

        rolesResponse.codeRole( roles.getCodeRole() );
        rolesResponse.nameRole( roles.getNameRole() );
        rolesResponse.permissions( permissionsSetToPermissionsResponseSet( roles.getPermissions() ) );

        return rolesResponse.build();
    }

    protected PermissionsResponse permissionsToPermissionsResponse(Permissions permissions) {
        if ( permissions == null ) {
            return null;
        }

        PermissionsResponse.PermissionsResponseBuilder permissionsResponse = PermissionsResponse.builder();

        permissionsResponse.codePermission( permissions.getCodePermission() );
        permissionsResponse.namePermission( permissions.getNamePermission() );
        permissionsResponse.description( permissions.getDescription() );

        return permissionsResponse.build();
    }

    protected Set<PermissionsResponse> permissionsSetToPermissionsResponseSet(Set<Permissions> set) {
        if ( set == null ) {
            return null;
        }

        Set<PermissionsResponse> set1 = new LinkedHashSet<PermissionsResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Permissions permissions : set ) {
            set1.add( permissionsToPermissionsResponse( permissions ) );
        }

        return set1;
    }
}
