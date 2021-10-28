package com.experis.de.MeFit.repositories;

import com.experis.de.MeFit.models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//In this repository the ProfileModel gets extended with JPA functions
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> getByContributorRequest(boolean status);
}
