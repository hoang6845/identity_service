package com.hoang.indentity_service.mapper;

import com.hoang.indentity_service.dto.request.UserCreationRequest;
import com.hoang.indentity_service.dto.response.PermissionsResponse;
import com.hoang.indentity_service.dto.response.RolesResponse;
import com.hoang.indentity_service.dto.response.UserResponse;
import com.hoang.indentity_service.entity.Permissions;
import com.hoang.indentity_service.entity.Roles;
import com.hoang.indentity_service.entity.UserEntity;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22 (Oracle Corporation)"
)
@Component
public class IUserMapperImpl implements IUserMapper {

    @Override
    public UserEntity toUserEntity(UserCreationRequest request) {
        if ( request == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.username( request.getUsername() );
        userEntity.password( request.getPassword() );
        userEntity.firstName( request.getFirstName() );
        userEntity.lastName( request.getLastName() );
        userEntity.birthDate( request.getBirthDate() );

        return userEntity.build();
    }

    @Override
    public void UpdateUserEntity(UserEntity userEntity, UserCreationRequest request) {
        if ( request == null ) {
            return;
        }

        userEntity.setPassword( request.getPassword() );
        userEntity.setFirstName( request.getFirstName() );
        userEntity.setLastName( request.getLastName() );
        userEntity.setBirthDate( request.getBirthDate() );
    }

    @Override
    public UserResponse toUserResponse(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( userEntity.getId() );
        userResponse.username( userEntity.getUsername() );
        userResponse.firstName( userEntity.getFirstName() );
        userResponse.lastName( userEntity.getLastName() );
        userResponse.birthDate( userEntity.getBirthDate() );
        userResponse.roles( rolesSetToRolesResponseSet( userEntity.getRoles() ) );

        userResponse.fullName( getFullName(userEntity.getFirstName(), userEntity.getLastName()) );

        return userResponse.build();
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

    protected RolesResponse rolesToRolesResponse(Roles roles) {
        if ( roles == null ) {
            return null;
        }

        RolesResponse.RolesResponseBuilder rolesResponse = RolesResponse.builder();

        rolesResponse.codeRole( roles.getCodeRole() );
        rolesResponse.nameRole( roles.getNameRole() );
        rolesResponse.permissions( permissionsSetToPermissionsResponseSet( roles.getPermissions() ) );

        return rolesResponse.build();
    }

    protected Set<RolesResponse> rolesSetToRolesResponseSet(Set<Roles> set) {
        if ( set == null ) {
            return null;
        }

        Set<RolesResponse> set1 = new LinkedHashSet<RolesResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Roles roles : set ) {
            set1.add( rolesToRolesResponse( roles ) );
        }

        return set1;
    }
}
