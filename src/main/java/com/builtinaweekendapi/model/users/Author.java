package com.builtinaweekendapi.model.users;

import com.builtinaweekendapi.model.BlogPost;
import com.builtinaweekendapi.model.SocialLinks;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Table(name = "user_author")
@EqualsAndHashCode(callSuper = true)
public class Author extends User {

    @Column(name = "email")
    @JsonProperty("email")
    private String emailAddress;

    @Column(name = "phone_number")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Column(name = "website_url")
    @JsonProperty("website_url")
    private String websiteUrl;

    @Column(nullable = false)
    @JsonProperty("biography")
    private String biography;

    @JsonProperty("social_links")
    @OneToMany(mappedBy = "author")
    private List<SocialLinks> socialLinks;

    @Column(name = "expertise")
    @JsonProperty("expertise")
    @ElementCollection
    @CollectionTable(name = "author_expertise")
    private List<String> areaOfExpertise;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "author")
    private List<BlogPost> blogPostList;

}
