package com.marketplace.userservice.controller;

import com.marketplace.common.dto.ApiResponse;
import com.marketplace.userservice.dto.AuthDto;
import com.marketplace.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ApiResponse<Long> signup(@RequestBody AuthDto.SignupRequest request) {
        return ApiResponse.success(authService.signup(request));
    }

    @PostMapping("/login")
    public ApiResponse<AuthDto.TokenResponse> login(@RequestBody AuthDto.LoginRequest request) {
        return ApiResponse.success(authService.login(request));
    }
}
