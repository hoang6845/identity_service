package com.hoang.indentity_service.mapper;

import com.hoang.indentity_service.dto.request.UserCreationRequest;
import com.hoang.indentity_service.dto.response.UserResponse;
import com.hoang.indentity_service.entity.UserEntity;
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

        UserEntity userEntity = new UserEntity();

        userEntity.setUsername( request.getUsername() );
        userEntity.setPassword( request.getPassword() );
        userEntity.setFirstName( request.getFirstName() );
        userEntity.setLastName( request.getLastName() );
        userEntity.setBirthDate( request.getBirthDate() );

        return userEntity;
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
        userResponse.password( userEntity.getPassword() );
        userResponse.firstName( userEntity.getFirstName() );
        userResponse.lastName( userEntity.getLastName() );
        userResponse.birthDate( userEntity.getBirthDate() );

        userResponse.fullName( getFullName(userEntity.getFirstName(), userEntity.getLastName()) );

        return userResponse.build();
    }
}
