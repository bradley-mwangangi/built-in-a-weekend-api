package com.builtinaweekendapi.blogActors.user;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Long id);
    Optional<User> findUserByEmail(String email);
    boolean existsById(@NonNull Long id);
    boolean existsByEmail(String email);
}
