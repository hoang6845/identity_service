package com.hoang.indentity_service.controller;

import com.hoang.indentity_service.dto.request.AuthenticationRequest;
import com.hoang.indentity_service.dto.request.IntrospectRequest;
import com.hoang.indentity_service.dto.request.LogoutRequest;
import com.hoang.indentity_service.dto.request.RefreshRequest;
import com.hoang.indentity_service.dto.response.ApiResponse;
import com.hoang.indentity_service.dto.response.AuthenticationReponse;
import com.hoang.indentity_service.dto.response.IntrospectResponse;
import com.hoang.indentity_service.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("auth/log-in")
    public ApiResponse<AuthenticationReponse> authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) throws JOSEException {
        AuthenticationReponse result = authenticationService.authenticate(authenticationRequest);
        return ApiResponse.<AuthenticationReponse>builder()
                .code(1000)
                .result(result)
                .build();
    }

    @PostMapping("/auth/introspect")
    public ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws JOSEException, ParseException {
        IntrospectResponse result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .code(1000)
                .result(result)
                .build();
    }

    @PostMapping("/auth/log-out")
    public ApiResponse<Void> logOut(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .code(1000)
                .build();
    }

    @PostMapping("auth/refresh")
    public ApiResponse<AuthenticationReponse> refreshRequest(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        AuthenticationReponse authenticationReponse = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationReponse>builder()
                .result(authenticationReponse)
                .code(1000)
                .build();
    }
}
