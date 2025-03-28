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
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class userController {

    UserService userService;

    @PostMapping("/users")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request){
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.CreateRequest(request));
        return response;
    }

    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> getUsers(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username : {}", authentication.getName());
        log.info("Roles : {}", authentication.getAuthorities());
        log.info("Id : {}", Optional.ofNullable(((Jwt) authentication.getPrincipal()).getClaim("Id")).orElse(""));
        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .result(userService.getAllUsers())
                .build();
    }

    @GetMapping("/users/{id}")
    public ApiResponse<UserResponse> getUser(@PathVariable String id){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.getUserById(id))
                .build();
    }

    @GetMapping("/users/myInfo")
    public ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.getMyInfo())
                .build();
    }

    @PutMapping("/users/{id}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String id, @RequestBody UserCreationRequest request){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.updateUser(id, request))
                .build();

    }

    @DeleteMapping("/users")
    public String deleteUser(@RequestBody List<String> Ids){
        if (userService.deleteUser(Ids)) return "Deleted";
        return "Not Deleted";
    }
}
