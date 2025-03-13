package com.hoang.indentity_service.controller;

import com.hoang.indentity_service.dto.response.ApiResponse;
import com.hoang.indentity_service.dto.request.UserCreationRequest;
import com.hoang.indentity_service.dto.response.UserResponse;
import com.hoang.indentity_service.entity.UserEntity;
import com.hoang.indentity_service.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class userController {

    UserService userService;

    @PostMapping("/users")
    public ApiResponse<UserEntity> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<UserEntity> response = new ApiResponse<>();
        response.setResult(userService.CreateRequest(request));
        return response;
    }

    @GetMapping("/users")
    public List<UserEntity> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable String id){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.getUserById(id))
                .build();
    }

    @PutMapping("/users/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String id, @RequestBody UserCreationRequest request){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.updateUser(id, request))
                .build();

    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable String id){
        if (userService.deleteUser(id)) return "Deleted";
        return "Not Deleted";
    }
}
