package com.experis.de.MeFit.service;

import com.experis.de.MeFit.models.GoalWorkout;

//GoalWorkout service interface
public interface GoalWorkoutService {

    GoalWorkout createGoalWorkout(GoalWorkout goalWorkout);

    //Function to delete the goalWorkout with nested data inside.
    void deleteGoalWorkout(GoalWorkout goalWorkout);
}
