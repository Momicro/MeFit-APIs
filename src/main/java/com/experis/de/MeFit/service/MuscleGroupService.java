package com.experis.de.MeFit.service;

import com.experis.de.MeFit.models.MuscleGroup;

//MuscleGroup service interface
public interface MuscleGroupService {

    //Function to create the MuscleGroup with nested data inside.
    MuscleGroup createMuscleGroup(MuscleGroup muscleGroup);

    //Function to delete the MuscleGroup with nested data inside.
    void deleteMuscleGroup(MuscleGroup muscleGroup);

    MuscleGroup updateMuscleGroupsExercise(int[] exerciseIds, String muscleGroupId);
}
