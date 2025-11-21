package com.marketplace.communityservice.repository;

import com.marketplace.communityservice.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
