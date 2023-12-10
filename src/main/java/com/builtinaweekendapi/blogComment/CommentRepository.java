package com.builtinaweekendapi.blogComment;

import com.builtinaweekendapi.blogPost.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findCommentById(Long commentId);
    List<Comment> findCommentByPost(BlogPost post);
}
