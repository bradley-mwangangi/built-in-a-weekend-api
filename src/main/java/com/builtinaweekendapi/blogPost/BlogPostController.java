package com.builtinaweekendapi.blogPost;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class BlogPostController {

    BlogPostService service;

    @Autowired
    public BlogPostController(BlogPostService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BlogPost>> getAllPosts() {
        return new ResponseEntity<>(service.getAllPosts(), HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<BlogPost> getPostById(@PathVariable("postId") Long postId) {
        BlogPost foundPost = service.getPostById(postId);
        return new ResponseEntity<>(foundPost, HttpStatus.OK);
    }

    @PostMapping("/create-post")
    public ResponseEntity<BlogPost> createPost(@RequestBody BlogPost blogPost) throws AccessDeniedException {
        return new ResponseEntity<>(service.createPost(blogPost), HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<BlogPost> updatePost(
            @PathVariable Long postId,
            @RequestBody BlogPost updatedPost
    ) {
        BlogPost thisPost = service.updatePost(postId, updatedPost);
        return new ResponseEntity<>(thisPost, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        service.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

}
