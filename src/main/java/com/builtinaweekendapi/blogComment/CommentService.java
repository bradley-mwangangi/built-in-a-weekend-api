package com.builtinaweekendapi.blogComment;

import com.builtinaweekendapi.blogActors.user.IUserRepository;
import com.builtinaweekendapi.blogActors.user.User;
import com.builtinaweekendapi.blogPost.BlogPost;
import com.builtinaweekendapi.blogPost.BlogPostRepository;
import com.builtinaweekendapi.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private final IUserRepository iUserRepository;
    private final BlogPostRepository blogPostRepository;
    private final CommentRepository commentRepository;


    @Autowired
    public CommentService(IUserRepository iUserRepository, CommentRepository commentRepository, BlogPostRepository blogPostRepository) {
        this.iUserRepository = iUserRepository;
        this.blogPostRepository = blogPostRepository;
        this.commentRepository = commentRepository;
    }

    List<Comment> getAllCommentsByPost(Long postId) {
        BlogPost post = blogPostRepository.findPostById(postId)
                .orElseThrow(() -> new NotFoundException("Post", postId));
        List<Comment> commentList = commentRepository.findCommentByPost(post);

        return new ArrayList<>(commentList);
    }

    Comment getCommentById(Long commentId) {
        return commentRepository.findCommentById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment", commentId));
    }

    Comment createComment(Long postId, Comment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User author = iUserRepository.findUserByEmail(username)
                .orElseThrow(() -> new NotFoundException("User", null));
        comment.setAuthor(author);

        BlogPost post = blogPostRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("Post", postId));
        comment.setPost(post);

        return commentRepository.save(comment);
    }

    Comment updateComment(Long commentId, Comment updatedComment) {
        Comment existingComment = getCommentById(commentId);
        existingComment.setContent(updatedComment.getContent());

        return commentRepository.save(existingComment);
    }

    void deleteComment(Long commentId) {
        if(!commentRepository.existsById(commentId)) {
            throw new NotFoundException("Comment", commentId);
        }

        commentRepository.deleteById(commentId);
    }

}
