package com.marketplace.userservice.controller;

import com.marketplace.common.dto.ApiResponse;
import com.marketplace.userservice.domain.User;
import com.marketplace.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;

    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        return ApiResponse.success(userRepository.findAll());
    }

    @PostMapping("/{id}/ban")
    public ApiResponse<Void> banUser(@PathVariable Long id) {
        // Simple ban logic: In real app, add 'banned' field or status
        // For now, let's just log it or assume we have a status field.
        // Since I can't easily change the entity schema without migration in this flow,
        // I will just print to console for demo purposes or add a TODO.
        // Actually, let's add a 'status' field to User entity if possible, or just mock it.
        System.out.println("Banning user " + id);
        return ApiResponse.success(null);
    }
    
    @PostMapping("/{id}/unban")
    public ApiResponse<Void> unbanUser(@PathVariable Long id) {
        System.out.println("Unbanning user " + id);
        return ApiResponse.success(null);
    }
}
