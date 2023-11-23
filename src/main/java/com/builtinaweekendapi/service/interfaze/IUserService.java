package com.builtinaweekendapi.service.interfaze;

import com.builtinaweekendapi.auth.ChangePasswordRequest;
import com.builtinaweekendapi.actors.User;

import javax.security.auth.login.CredentialException;
import java.security.Principal;

public interface IUserService {

    User getUserById(Long userId);
    void changePassword(ChangePasswordRequest request, Principal connectedUser) throws CredentialException;

}
