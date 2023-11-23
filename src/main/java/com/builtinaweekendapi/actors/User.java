package com.builtinaweekendapi.actors;

import com.builtinaweekendapi.model.BaseEntity;
import com.builtinaweekendapi.model.Comment;
import com.builtinaweekendapi.model.enums.Role;
import com.builtinaweekendapi.model.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_appuser")
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false)
    @JsonProperty("first_name")
    @NotNull(message = "first name cannot be null")
    @NotBlank(message = "first name must not be empty")
    private String firstName;

    @Column(nullable = false)
    @JsonProperty("last_name")
    @NotNull(message = "last name cannot be null")
    @NotBlank(message = "last name must not be empty")
    private String lastName;

    @Column(nullable = false, unique = true)
    @Email(message = "Invalid email!")
    @NotNull(message = "email cannot be null")
    @NotBlank(message = "email must not be empty")
    private String email;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "password cannot be null")
    @NotBlank(message = "Password must not be empty")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "author")
    private List<Comment> commentList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.addAll(role.getAuthorities());
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
