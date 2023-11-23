package com.builtinaweekendapi.service;

import com.builtinaweekendapi.auth.ChangePasswordRequest;
import com.builtinaweekendapi.exceptions.NotFoundException;
import com.builtinaweekendapi.actors.User;
import com.builtinaweekendapi.repository.UserRepository;
import com.builtinaweekendapi.service.interfaze.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserById(Long userId) {
        //TODO - consider using JpaRepository getReferenceById(ID id)
        return userRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User", userId));
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) throws CredentialException {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!passwordEncoder.matches(request.getExistingPassword(), user.getPassword())) {
            throw new CredentialException();
        }

        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User %s not found", email)));
    }
}
