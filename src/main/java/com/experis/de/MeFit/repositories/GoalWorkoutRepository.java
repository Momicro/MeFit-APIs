package com.experis.de.MeFit.repositories;

import com.experis.de.MeFit.models.GoalWorkout;
import com.experis.de.MeFit.models.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;

//In this repository the GoalWorkoutmodel gets extended with JPA functions
@Repository
public interface GoalWorkoutRepository extends JpaRepository<GoalWorkout, Long> {

    @Query("select g from GoalWorkout g where g.goal.id = ?1 and g.workout.id = ?2")
    GoalWorkout get(Long goalId, Long workoutId);

    @Query("select g.workout from GoalWorkout g where g.goal.id = ?1 and g.completed = ?2")
    List<Workout> get(Long goalId, Boolean completed);
}
