package com.builtinaweekendapi.blogPost;

import com.builtinaweekendapi.blogActors.user.User;
import com.builtinaweekendapi.blogBase.BaseEntity;
import com.builtinaweekendapi.blogComment.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.util.List;


@Entity
@Data
@Table(name = "blogpost")
@EqualsAndHashCode(callSuper = true)
public class BlogPost extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "post")
    private List<Comment> commentList;

}
