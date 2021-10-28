package com.experis.de.MeFit.service;

import com.experis.de.MeFit.models.Workout;

//Workout service interface
public interface WorkoutService {

    //Function to create a new workout with nested data inside.
    Workout createWorkout(Workout workout);

    //Function to delete the workout with nested data inside.
    void deleteWorkout(Workout workout);

    Workout updateWorkoutsExercise(int[] exerciseIds, String workoutId);

}
