package com.marketplace.userservice.service;

import com.marketplace.common.exception.BusinessException;
import com.marketplace.common.exception.GlobalErrorCode;
import com.marketplace.userservice.config.JwtTokenProvider;
import com.marketplace.userservice.domain.Role;
import com.marketplace.userservice.domain.User;
import com.marketplace.userservice.dto.AuthDto;
import com.marketplace.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Long signup(AuthDto.SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(GlobalErrorCode.INVALID_INPUT, "Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .role(Role.USER)
                .build();

        return userRepository.save(user).getId();
    }

    @Transactional(readOnly = true)
    public AuthDto.TokenResponse login(AuthDto.LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(GlobalErrorCode.NOT_FOUND, "User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(GlobalErrorCode.UNAUTHORIZED, "Invalid password");
        }

        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());
        return new AuthDto.TokenResponse(token);
    }
}
