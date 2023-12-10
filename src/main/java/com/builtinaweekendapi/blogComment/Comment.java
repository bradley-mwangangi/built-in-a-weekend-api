package com.builtinaweekendapi.blogComment;

import com.builtinaweekendapi.blogActors.user.User;
import com.builtinaweekendapi.blogBase.BaseEntity;
import com.builtinaweekendapi.blogPost.BlogPost;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({"content"})
public class Comment extends BaseEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private BlogPost post;

}
