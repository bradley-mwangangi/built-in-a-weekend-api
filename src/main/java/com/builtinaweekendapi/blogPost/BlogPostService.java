package com.builtinaweekendapi.blogPost;

import com.builtinaweekendapi.blogActors.author.Author;
import com.builtinaweekendapi.blogActors.user.IUserRepository;
import com.builtinaweekendapi.blogActors.user.User;
import com.builtinaweekendapi.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlogPostService {

    private final BlogPostRepository repository;
    private final IUserRepository iUserRepository;

    @Autowired
    public BlogPostService(BlogPostRepository repository, IUserRepository iUserRepository) {
        this.repository = repository;
        this.iUserRepository = iUserRepository;
    }

    User isAuthor(User user) throws AccessDeniedException {
        if (!(user instanceof Author)) {
            throw new AccessDeniedException("User not an author! Unauthorized to perform this action.");
        }

        return user;
    }

    List<BlogPost> getAllPosts() {
        List<BlogPost> blogPostList = repository.findAll();
        return new ArrayList<>(blogPostList);
    }

    BlogPost getPostById(Long postId) {
        return repository.findPostById(postId)
                .orElseThrow(() -> new NotFoundException("Post", postId));
    }

    BlogPost createPost(BlogPost blogPost) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = iUserRepository.findUserByEmail(username)
                .orElseThrow(() -> new NotFoundException("User", null));
        User author = isAuthor(user);
        blogPost.setAuthor(author);

        return repository.save(blogPost);
    }

    BlogPost updatePost(Long postId, BlogPost updatedPost) {
        BlogPost existingPost = getPostById(postId);
        existingPost.setTitle(updatedPost.getTitle());
        existingPost.setContent(updatedPost.getContent());

        return repository.save(existingPost);
    }

    void deletePost(Long postId) {
        if(!repository.existsById(postId)) {
            throw new NotFoundException("Post", postId);
        }

        repository.deleteById(postId);
    }

}
