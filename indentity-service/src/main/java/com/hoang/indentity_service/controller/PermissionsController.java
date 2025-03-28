package com.hoang.indentity_service.controller;

import com.hoang.indentity_service.dto.request.PermissionsRequest;
import com.hoang.indentity_service.dto.response.ApiResponse;
import com.hoang.indentity_service.dto.response.PermissionsResponse;
import com.hoang.indentity_service.repository.PermissionsRepository;
import com.hoang.indentity_service.service.PermissionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
public class PermissionsController {
    private final PermissionsService permissionsService;

    @PostMapping
    ApiResponse<PermissionsResponse> create(@RequestBody PermissionsRequest permissionsRequest) {
        return ApiResponse.<PermissionsResponse>builder()
                .code(1000)
                .result(permissionsService.createPermissions(permissionsRequest))
                .build();
    }

    @GetMapping
    ApiResponse<List<PermissionsResponse>> getAllPermissions() {
        return ApiResponse.<List<PermissionsResponse>>builder()
                .result(permissionsService.getAll())
                .code(1000)
                .build();
    }

    @DeleteMapping("/{codePermission}")
    ApiResponse<Void> delete(@PathVariable String codePermission) {
        permissionsService.deletePermissions(codePermission);
        return ApiResponse.<Void>builder().build();
    }
}
