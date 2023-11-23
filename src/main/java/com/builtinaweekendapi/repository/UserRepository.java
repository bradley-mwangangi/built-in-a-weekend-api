package com.builtinaweekendapi.repository;

import com.builtinaweekendapi.actors.User;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Long id);
    Optional<User> findUserByEmail(String email);
    boolean existsById(@NonNull Long id);
    boolean existsByEmail(String email);
}
