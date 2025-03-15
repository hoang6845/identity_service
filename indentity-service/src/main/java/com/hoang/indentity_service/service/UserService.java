package com.hoang.indentity_service.service;

import com.hoang.indentity_service.dto.request.UserCreationRequest;
import com.hoang.indentity_service.dto.response.UserResponse;
import com.hoang.indentity_service.entity.UserEntity;
import com.hoang.indentity_service.exception.AppException;
import com.hoang.indentity_service.exception.ErrorCode;
import com.hoang.indentity_service.mapper.IUserMapper;
import com.hoang.indentity_service.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;

    IUserMapper userMapper;

    public UserEntity CreateRequest(UserCreationRequest request) {
        if (userRepository.existsByUsername((request.getUsername())))
            throw new AppException(ErrorCode.USER_EXISTED);
        UserEntity userEntity = userMapper.toUserEntity(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(userEntity);
    }

    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

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

    public boolean deleteUser(String id){
        UserEntity u = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.deleteById(id);
        return true;
    }
}
