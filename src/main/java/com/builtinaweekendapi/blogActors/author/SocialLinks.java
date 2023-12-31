package com.builtinaweekendapi.blogActors.author;

import com.builtinaweekendapi.blogBase.BaseEntity;
import com.builtinaweekendapi.blogActors.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class SocialLinks extends BaseEntity {

    private String title;

    private String linkUrl;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

}
