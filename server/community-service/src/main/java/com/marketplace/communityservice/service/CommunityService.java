package com.marketplace.communityservice.service;

import com.marketplace.common.exception.BusinessException;
import com.marketplace.common.exception.GlobalErrorCode;
import com.marketplace.communityservice.domain.Comment;
import com.marketplace.communityservice.domain.Post;
import com.marketplace.communityservice.dto.CommunityDto;
import com.marketplace.communityservice.repository.CommentRepository;
import com.marketplace.communityservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long createPost(CommunityDto.CreatePostRequest request) {
        Post post = Post.builder()
                .authorId(request.getAuthorId())
                .title(request.getTitle())
                .content(request.getContent())
                .category(request.getCategory())
                .build();

        return postRepository.save(post).getId();
    }

    @Transactional(readOnly = true)
    public List<CommunityDto.PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::mapToPostResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommunityDto.PostResponse getPostDetail(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(GlobalErrorCode.NOT_FOUND, "Post not found"));
        
        post.increaseViewCount();
        return mapToPostResponse(post);
    }

    @Transactional
    public Long createComment(Long postId, CommunityDto.CreateCommentRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(GlobalErrorCode.NOT_FOUND, "Post not found"));

        Comment parent = null;
        if (request.getParentId() != null) {
            parent = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new BusinessException(GlobalErrorCode.NOT_FOUND, "Parent comment not found"));
        }

        Comment comment = Comment.builder()
                .post(post)
                .authorId(request.getAuthorId())
                .content(request.getContent())
                .parent(parent)
                .build();

        return commentRepository.save(comment).getId();
    }

    @Transactional(readOnly = true)
    public List<CommunityDto.CommentResponse> getComments(Long postId) {
        return commentRepository.findByPostIdAndParentIsNull(postId).stream()
                .map(this::mapToCommentResponse)
                .collect(Collectors.toList());
    }

    private CommunityDto.PostResponse mapToPostResponse(Post post) {
        return CommunityDto.PostResponse.builder()
                .id(post.getId())
                .authorId(post.getAuthorId())
                .title(post.getTitle())
                .content(post.getContent())
                .category(post.getCategory())
                .viewCount(post.getViewCount())
                .createdAt(post.getCreatedAt())
                .build();
    }

    private CommunityDto.CommentResponse mapToCommentResponse(Comment comment) {
        return CommunityDto.CommentResponse.builder()
                .id(comment.getId())
                .authorId(comment.getAuthorId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .children(comment.getChildren().stream()
                        .map(this::mapToCommentResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
