package com.builtinaweekendapi.blogActors.user.enums;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum Role {

    GUEST(
            // guest view. can only read posts and comments
            Set.of(
                    // has only post::read permission
                    Permission.POST_READ,
                    // has only comment::read permission
                    Permission.COMMENT_READ
            )
    ),
    USER(
            // default logged-in user. can read and comment on posts
            // permissions LIMITED to OWN account
            Set.of(
                    // has only post::read permission
                    Permission.POST_READ,
                    // comment::create:read:update:delete
                    Permission.COMMENT_CRUD
            )
    ),
    CONTRIBUTOR(
            // submits content for review. published if approved. can not publish directly
            // permissions LIMITED to OWN account
            Set.of(
                    // post::create:read:update:delete, post::submit_for_review
                    Permission.POST_CRUD, Permission.POST_SUBMIT_for_REVIEW,
                    // comment::create:read:update:delete
                    Permission.COMMENT_CRUD
            )
    ),
    AUTHOR(
            // creates content for publication. can submit for review or publish directly
            // permissions LIMITED to OWN account
            Set.of(
                    // all post-related permissions
                    Permission.POST_CRUD, Permission.POST_SUBMIT_for_REVIEW, Permission.POST_PUBLISH,
                    // comment::create:read:update:delete
                    Permission.COMMENT_CRUD
            )
    ),
    MODERATOR(
            // moderates user content. cannot create posts
            // permissions EXTENDED to OTHER user accounts
            Set.of(
                    // POST_CRUD except CREATE, post::submit_for_review, post::publish
                    Permission.POST_READ, Permission.POST_UPDATE, Permission.POST_DELETE, Permission.POST_SUBMIT_for_REVIEW, Permission.POST_PUBLISH,
                    // comment::create:read:update:delete
                    Permission.COMMENT_CRUD
            )
    ),
    REVIEWER(
            // reviews CONTRIBUTOR submissions and approves them
            // permissions EXTENDED to OTHER user accounts
            Set.of(
                    // all post-related permissions
                    Permission.POST_CRUD, Permission.POST_SUBMIT_for_REVIEW, Permission.POST_PUBLISH,
                    // comment::create:read:update:delete
                    Permission.COMMENT_CRUD
            )
    ),
    EDITOR(
            // oversees whole editorial process
            // permissions EXTENDED to OTHER user accounts
            Set.of(
                    // all post-related permissions
                    Permission.POST_CRUD, Permission.POST_SUBMIT_for_REVIEW, Permission.POST_PUBLISH,
                    // comment::create:read:update:delete
                    Permission.COMMENT_CRUD
            )
    ),
    SUPER_ADMIN(
            // manages user accounts, blogposts, and comments
            // permissions EXTENDED to OTHER user accounts
            Set.of(
                    Permission.RESOURCE_CREATE,
                    Permission.RESOURCE_READ,
                    Permission.RESOURCE_UPDATE,
                    Permission.RESOURCE_DELETE
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
