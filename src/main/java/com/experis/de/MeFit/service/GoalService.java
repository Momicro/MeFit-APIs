package com.experis.de.MeFit.service;

import com.experis.de.MeFit.models.Goal;
import com.experis.de.MeFit.models.Workout;

import java.util.List;

//Goal service interface
public interface GoalService {

    Goal createGoal(Goal goal);

    //Function to delete the goal with nested data inside.
    void deleteGoal(Goal goal);

    void setWorkout(Long goalId, Long workoutId, Boolean completed);

    void setProgram(Long goalId, Long programId, Boolean completed);

    List<Workout> getWorkouts(Long goalId, Boolean completed);
}
