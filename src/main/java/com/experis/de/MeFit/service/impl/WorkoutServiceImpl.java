package com.experis.de.MeFit.service.impl;

import com.experis.de.MeFit.models.Exercise;
import com.experis.de.MeFit.models.MuscleGroup;
import com.experis.de.MeFit.models.Workout;
import com.experis.de.MeFit.repositories.ExerciseRepository;
import com.experis.de.MeFit.repositories.WorkoutRepository;
import com.experis.de.MeFit.service.WorkoutService;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Override
    public Workout createWorkout(Workout workout) {
        return null;
    }

    @Override
    public void deleteWorkout(Workout workout) {

    }

    //First concatenate the Array with the List to then add it back to the workout and return the updated exercise in the RestController
    @Override
    @JsonSetter
    public Workout updateWorkoutsExercise(int[] exerciseIds, String workoutId) {
        Workout workout = workoutRepository.getById(Long.valueOf(workoutId));
        Set<Exercise> exercises = workout.getExercises();

        for (int i : exerciseIds){
            Exercise exercise = exerciseRepository.getById(Long.parseLong(String.valueOf(i)));
            exercises.add(exercise);
            exercise.getWorkouts().add(workout);
        }
        workout.setExercises(exercises);

        return workoutRepository.save(workout);
    }

}
