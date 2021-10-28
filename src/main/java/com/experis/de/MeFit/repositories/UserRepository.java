package com.experis.de.MeFit.repositories;

import com.experis.de.MeFit.models.Exercise;
import com.experis.de.MeFit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//In this repository the UserModel gets extended with JPA functions
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User getByEmail(String email);
    User getById(Long userId);

    @Query("select u from User u where u.profile.contributorRequest = true")
    List<User> getUsersWithContributorRequest();
}
