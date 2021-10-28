package com.experis.de.MeFit.service;

import com.experis.de.MeFit.models.Goal;
import com.experis.de.MeFit.models.Profile;

public interface ProfileService {
    //Function to create the profile with nested data inside.
    Goal createProfile(Profile profile);

    //Function to delete the profile with nested data inside.
    void deleteProfile(Profile profile);
}
