package com.hoang.indentity_service.controller;

import com.hoang.indentity_service.dto.request.RolesRequest;
import com.hoang.indentity_service.dto.response.ApiResponse;
import com.hoang.indentity_service.dto.response.RolesResponse;
import com.hoang.indentity_service.service.RolesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("roles")
public class RolesController {
    private final RolesService rolesService;

    @PostMapping
    public ApiResponse<RolesResponse> createRoles(@RequestBody RolesRequest request) {
        System.out.println("vào controller");
        return ApiResponse.<RolesResponse>builder()
                .code(1000)
                .result(rolesService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<RolesResponse>> getALlRoles() {
        return ApiResponse.<List<RolesResponse>>builder()
                .code(1000)
                .result(rolesService.findAll())
                .build();
    }

    @DeleteMapping("/{codeRole}")
    public ApiResponse<Void> DeleteRoles(@PathVariable String codeRole){
        rolesService.delete(codeRole);
        return ApiResponse.<Void>builder().build();
    }
}
