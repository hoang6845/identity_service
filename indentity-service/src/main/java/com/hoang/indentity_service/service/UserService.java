package com.hoang.indentity_service.service;

import com.hoang.indentity_service.dto.request.UserCreationRequest;
import com.hoang.indentity_service.dto.response.UserResponse;
import com.hoang.indentity_service.entity.Roles;
import com.hoang.indentity_service.entity.UserEntity;
import com.hoang.indentity_service.enums.Role;
import com.hoang.indentity_service.exception.AppException;
import com.hoang.indentity_service.exception.ErrorCode;
import com.hoang.indentity_service.mapper.IUserMapper;
import com.hoang.indentity_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    IUserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserEntity CreateRequest(UserCreationRequest request) {
        if (userRepository.existsByUsername((request.getUsername())))
            throw new AppException(ErrorCode.USER_EXISTED);
        UserEntity userEntity = userMapper.toUserEntity(request);
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setRole(new Roles(Role.ROLE_USER.getCodeRole(), Role.ROLE_USER.getNameRole()));
        return userRepository.save(userEntity);
    }

    public UserResponse getMyInfo(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        //khi 1 cái request được xác thực, thì thông tin user đăng nhập lưu trữ trong SecurityContextHolder
        // getContext để lấy user hiện tại
        Authentication authentication = securityContext.getAuthentication();
        String name = authentication.getName();

        UserEntity u =userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(u);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    @PostAuthorize("returnObject.username == authentication.name || hasAuthority('ROLE_ADMIN')")
    public UserResponse getUserById(String id){
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    public UserResponse updateUser(String id, UserCreationRequest request){
        UserEntity u = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        request.setUsername(null);
        userMapper.UpdateUserEntity(u, request);
        return userMapper.toUserResponse(userRepository.save(u));
    }

    //chỉ có admin mới được xóa, khi xóa thì kiểm tra trong danh sách ids để không xóa admin bằng filter
    @Transactional(rollbackOn = AppException.class)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PreFilter("filterObject != T(org.springframework.security.core.context.SecurityContextHolder).getContext().getAuthentication().getPrincipal().getClaim('Id')")
    public boolean deleteUser(List<String> ids){
        for (String id:ids){
            UserEntity u = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
            userRepository.deleteById(id);
        }
        return true;
    }
}
