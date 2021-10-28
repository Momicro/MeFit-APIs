package com.experis.de.MeFit.service;

import com.experis.de.MeFit.models.Exercise;

//exercise service interface
public interface ExerciseService {

    //Exercise createExercise(Exercise exercise);

    //First concatenate the Array with the List to then add it back to the Exercise and return the updated exercise in the RestController
    Exercise updateExerciseWorkouts(int[] muscleGroupIds, String exerciseId);

    //Function to delete the exercises with nested data inside.
    void deleteExercise(Exercise exercise);

    boolean checkIfExerciseExists(String name);
}
