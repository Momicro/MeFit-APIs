package com.experis.de.MeFit.service;

import com.experis.de.MeFit.models.User;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

//User service interface
public interface UserService {

    boolean checkIfUserExists(String email);

    User getUserByEmail(String email);

    User getUserById(Long userId);

    void updateUser(User user);

    User createNewUserProfileFromJWT(Jwt principal);

    Boolean deleleUserById(Long userId);

    List<User> getUserWithContributorRequests();

    void setContributorRequests(Long userId, Boolean requestStatus);
}
