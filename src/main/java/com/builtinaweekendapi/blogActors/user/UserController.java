package com.builtinaweekendapi.blogActors.user;

import com.builtinaweekendapi.auth.ChangePasswordRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long userId) {
        User foundUser = userService.getUserById(userId);
        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<User> getUser() {
        User foundUser = userService.getAuthenticatedUser();
        if (foundUser == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return new ResponseEntity<>(foundUser, HttpStatus.OK);
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest,
            Principal connectedUser
    ) {
        try {
            userService.changePassword(changePasswordRequest, connectedUser);
        } catch (CredentialException e) {
            ProblemDetail problemDetails = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, "Invalid credentials");
            return new ResponseEntity<>(problemDetails, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
    }

}
