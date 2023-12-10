package com.builtinaweekendapi.blogComment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post/{postId}/comment")
public class CommentController {

    final CommentService service;

    @Autowired
    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Comment>> getAllCommentsByPost(@PathVariable("postId") Long postId) {
        return new ResponseEntity<>(service.getAllCommentsByPost(postId), HttpStatus.OK);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable("commentId") Long commentId) {
        Comment foundComment = service.getCommentById(commentId);
        return new ResponseEntity<>(foundComment, HttpStatus.OK);
    }

    @PostMapping("/create-comment")
    public ResponseEntity<Comment> createComment(
            @PathVariable("postId") Long postId,
            @RequestBody Comment comment

    ) {
        return new ResponseEntity<>(service.createComment(postId, comment), HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long commentId,
            @RequestBody Comment updatedComment
    ) {
        Comment thisComment = service.updateComment(commentId, updatedComment);
        return new ResponseEntity<>(thisComment, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        service.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

}
