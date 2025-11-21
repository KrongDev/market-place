package com.marketplace.communityservice.controller;

import com.marketplace.common.dto.ApiResponse;
import com.marketplace.communityservice.dto.CommunityDto;
import com.marketplace.communityservice.service.CommunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping
    public ApiResponse<Long> createPost(@RequestBody CommunityDto.CreatePostRequest request) {
        return ApiResponse.success(communityService.createPost(request));
    }

    @GetMapping
    public ApiResponse<List<CommunityDto.PostResponse>> getAllPosts() {
        return ApiResponse.success(communityService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ApiResponse<CommunityDto.PostResponse> getPostDetail(@PathVariable Long id) {
        return ApiResponse.success(communityService.getPostDetail(id));
    }

    @PostMapping("/{id}/comments")
    public ApiResponse<Long> createComment(@PathVariable Long id, @RequestBody CommunityDto.CreateCommentRequest request) {
        return ApiResponse.success(communityService.createComment(id, request));
    }

    @GetMapping("/{id}/comments")
    public ApiResponse<List<CommunityDto.CommentResponse>> getComments(@PathVariable Long id) {
        return ApiResponse.success(communityService.getComments(id));
    }
}
