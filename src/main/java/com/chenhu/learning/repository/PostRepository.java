package com.chenhu.learning.repository;

import com.chenhu.learning.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 陈虎
 * @since 2022-06-02 14:39
 */
public interface PostRepository extends JpaRepository<Post, Long> {
}
