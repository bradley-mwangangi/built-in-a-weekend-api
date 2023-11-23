package com.builtinaweekendapi.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    /** managers can be assigned individual permissions
     * say, _CREATE, _READ, _UPDATE, _DELETE; or entire _CRUD permission
     */
    USER_CREATE("user::create"),
    USER_READ("user::read"),
    USER_UPDATE("user::update"),
    USER_DELETE("user::delete"),
    USER_CRUD("user::create:read:update:delete"),

    POST_CREATE("post::create"),
    POST_READ("post::read"),
    POST_UPDATE("post::update"),
    POST_DELETE("post::delete"),
    POST_CRUD("post::create:read:update:delete"),

    POST_SUBMIT_for_REVIEW("post::submit_for_review"),
    POST_PUBLISH("post::publish"),

    COMMENT_READ("comment::read"),
    COMMENT_CRUD("comment::create:read:update:delete"),

    /** admin permissions. applies to all resources
     * super_admin has all permissions
     * other admins can be assigned individual or all permissions
     */
    RESOURCE_CREATE("resource::create"),
    RESOURCE_READ("resource::read"),
    RESOURCE_UPDATE("resource::update"),
    RESOURCE_DELETE("resource::delete");

    private final String permission;

}
