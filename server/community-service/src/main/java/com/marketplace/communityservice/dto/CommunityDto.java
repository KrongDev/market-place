package com.marketplace.communityservice.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class CommunityDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatePostRequest {
        private Long authorId;
        private String title;
        private String content;
        private String category;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateCommentRequest {
        private Long authorId;
        private String content;
        private Long parentId; // Optional
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostResponse {
        private Long id;
        private Long authorId;
        private String title;
        private String content;
        private String category;
        private Long viewCount;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CommentResponse {
        private Long id;
        private Long authorId;
        private String content;
        private List<CommentResponse> children;
        private LocalDateTime createdAt;
    }
}
