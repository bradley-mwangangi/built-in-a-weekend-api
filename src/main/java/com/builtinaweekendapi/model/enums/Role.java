package com.builtinaweekendapi.entity.enums;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.builtinaweekendapi.entity.enums.Permission.*;

@Getter
public enum Role {

    GUEST(
            // guest view. can only read posts and comments
            Set.of(
                    // has only post::read permission
                    POST_READ,
                    // has only comment::read permission
                    COMMENT_READ
            )
    ),
    USER(
            // default logged-in user. can read and comment on posts
            // permissions LIMITED to OWN account
            Set.of(
                    // has only post::read permission
                    POST_READ,
                    // comment::create:read:update:delete
                    COMMENT_CRUD
            )
    ),
    CONTRIBUTOR(
            // submits content for review. published if approved. can not publish directly
            // permissions LIMITED to OWN account
            Set.of(
                    // post::create:read:update:delete, post::submit_for_review
                    POST_CRUD, POST_SUBMIT_for_REVIEW,
                    // comment::create:read:update:delete
                    COMMENT_CRUD
            )
    ),
    AUTHOR(
            // creates content for publication. can submit for review or publish directly
            // permissions LIMITED to OWN account
            Set.of(
                    // all post-related permissions
                    POST_CRUD, POST_SUBMIT_for_REVIEW, POST_PUBLISH,
                    // comment::create:read:update:delete
                    COMMENT_CRUD
            )
    ),
    MODERATOR(
            // moderates user content. cannot create posts
            // permissions EXTENDED to OTHER user accounts
            Set.of(
                    // POST_CRUD except CREATE, post::submit_for_review, post::publish
                    POST_READ, POST_UPDATE, POST_DELETE, POST_SUBMIT_for_REVIEW, POST_PUBLISH,
                    // comment::create:read:update:delete
                    COMMENT_CRUD
            )
    ),
    REVIEWER(
            // reviews CONTRIBUTOR submissions and approves them
            // permissions EXTENDED to OTHER user accounts
            Set.of(
                    // all post-related permissions
                    POST_CRUD, POST_SUBMIT_for_REVIEW, POST_PUBLISH,
                    // comment::create:read:update:delete
                    COMMENT_CRUD
            )
    ),
    EDITOR(
            // oversees whole editorial process
            // permissions EXTENDED to OTHER user accounts
            Set.of(
                    // all post-related permissions
                    POST_CRUD, POST_SUBMIT_for_REVIEW, POST_PUBLISH,
                    // comment::create:read:update:delete
                    COMMENT_CRUD
            )
    ),
    SUPER_ADMIN(
            // manages user accounts, blogposts, and comments
            // permissions EXTENDED to OTHER user accounts
            Set.of(
                    RESOURCE_CREATE,
                    RESOURCE_READ,
                    RESOURCE_UPDATE,
                    RESOURCE_DELETE
            )
    );

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }

}
