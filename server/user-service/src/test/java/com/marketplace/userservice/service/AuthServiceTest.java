package com.marketplace.userservice.service;

import com.marketplace.common.exception.BusinessException;
import com.marketplace.common.exception.GlobalErrorCode;
import com.marketplace.userservice.config.JwtTokenProvider;
import com.marketplace.userservice.domain.Role;
import com.marketplace.userservice.domain.User;
import com.marketplace.userservice.dto.AuthDto;
import com.marketplace.userservice.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("Signup Success")
    void signup_success() {
        // given
        AuthDto.SignupRequest request = new AuthDto.SignupRequest("test@test.com", "password", "nickname");
        User user = User.builder().id(1L).email("test@test.com").role(Role.USER).build();

        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // when
        Long userId = authService.signup(request);

        // then
        assertEquals(1L, userId);
    }

    @Test
    @DisplayName("Signup Fail - Duplicate Email")
    void signup_fail_duplicate_email() {
        // given
        AuthDto.SignupRequest request = new AuthDto.SignupRequest("test@test.com", "password", "nickname");
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // when & then
        BusinessException exception = assertThrows(BusinessException.class, () -> authService.signup(request));
        assertEquals(GlobalErrorCode.INVALID_INPUT, exception.getErrorCode());
    }

    @Test
    @DisplayName("Login Success")
    void login_success() {
        // given
        AuthDto.LoginRequest request = new AuthDto.LoginRequest("test@test.com", "password");
        User user = User.builder().email("test@test.com").password("encodedPassword").role(Role.USER).build();

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtTokenProvider.createToken(user.getEmail(), "USER")).thenReturn("accessToken");

        // when
        AuthDto.TokenResponse response = authService.login(request);

        // then
        assertNotNull(response);
        assertEquals("accessToken", response.getAccessToken());
    }
}
