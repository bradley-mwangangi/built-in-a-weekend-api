package com.builtinaweekendapi.blogActors.user;

import com.builtinaweekendapi.auth.ChangePasswordRequest;
import com.builtinaweekendapi.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final IUserRepository IUserRepository;
    private final PasswordEncoder passwordEncoder;

    public User getUserById(Long userId) {
        //TODO - consider using JpaRepository getReferenceById(ID id)
        return IUserRepository.findUserById(userId)
                .orElseThrow(() -> new NotFoundException("User", userId));
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return IUserRepository.findUserByEmail(email).orElse(null);
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
        IUserRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return IUserRepository.findUserByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("User %s not found", email)));
    }
}
