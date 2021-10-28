package com.experis.de.MeFit.service.impl;

import com.experis.de.MeFit.models.Profile;
import com.experis.de.MeFit.models.User;
import com.experis.de.MeFit.repositories.ProfileRepository;
import com.experis.de.MeFit.repositories.UserRepository;
import com.experis.de.MeFit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ProfileRepository profileRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public boolean checkIfUserExists(String email){
        return userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email){
        return userRepository.getByEmail(email);
    }

    public User getUserById(Long userId) {
        var user = userRepository.findById(userId);
        return (user.isPresent() ? user.get() : null);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public User createNewUserProfileFromJWT(Jwt principal)
    {
        User newUser = new User();
        newUser.setEmail(principal.getClaimAsString("email"));
        newUser.setLastName(principal.getClaimAsString("given_name"));
        newUser.setFirstName(principal.getClaimAsString("family_name"));

        // create and add new empty profile
        var profile = new Profile();
        profile = profileRepository.save(profile);
        newUser.setProfile(profile);

        // get roles for client
        /*
        String resourceId = "MeFit";

        Map<String, Object> resourceAccess = principal.getClaim("resource_access");
        Map<String, Object> resource;
        Collection<String> resourceRoles;
        if (resourceAccess != null && (resource = (Map<String, Object>)resourceAccess.get(resourceId)) != null) {
            resourceRoles = (Collection<String>) resource.get("roles");
        }
        */

        /*
        HashSet<Role> dbRoles = new HashSet<>();
        for (String role: roles) {
            dbRoles.add(roleRepository.getRoleByName(role));
        }
        newUser.setRoles(dbRoles);
        */

        newUser = userRepository.save(newUser);

        return newUser;
    }

    public Boolean deleleUserById(Long userId) {
        if (!userRepository.existsById(userId)) {
            return false;
        }

        userRepository.deleteById(userId);
        return true;
    }

    public List<User> getUserWithContributorRequests() {
        return userRepository.getUsersWithContributorRequest();
    }

    public void setContributorRequests(Long userId, Boolean requestStatus) {
        var user = userRepository.getById(userId);
        user.getProfile().setContributorRequest(requestStatus);
        
        userRepository.save(user);
    }

}
