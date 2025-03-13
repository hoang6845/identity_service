package com.hoang.indentity_service.mapper;

import com.hoang.indentity_service.dto.request.UserCreationRequest;
import com.hoang.indentity_service.dto.response.UserResponse;
import com.hoang.indentity_service.entity.UserEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface IUserMapper {
    UserEntity toUserEntity(UserCreationRequest request);


    @Mapping(target = "username", ignore = true)
    void UpdateUserEntity(@MappingTarget UserEntity userEntity, UserCreationRequest request);

    //   @Mapping(target = "fullName", expression = "java(userEntity.getFirstName()+ \" \" + userEntity.getLastName())")
    @Mapping(target = "fullName", expression = "java(getFullName(userEntity.getFirstName(), userEntity.getLastName()))")
    UserResponse toUserResponse(UserEntity userEntity);

    default String getFullName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }
}