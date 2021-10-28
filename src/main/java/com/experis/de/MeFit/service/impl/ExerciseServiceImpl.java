package com.experis.de.MeFit.service.impl;

import com.experis.de.MeFit.models.Exercise;
import com.experis.de.MeFit.models.MuscleGroup;
import com.experis.de.MeFit.models.Workout;
import com.experis.de.MeFit.repositories.ExerciseRepository;
import com.experis.de.MeFit.repositories.MuscleGroupRepository;
import com.experis.de.MeFit.repositories.WorkoutRepository;
import com.experis.de.MeFit.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    //Get the JPA Services
    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    MuscleGroupRepository muscleGroupRepository;

    /*
    @Override
    public Exercise createExercise(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }*/

     public boolean checkIfExerciseExists(String name){ return exerciseRepository.existsByName(name); }

    @Override
    public Exercise updateExerciseWorkouts(int[] workoutIds, String exerciseId) {

        Exercise exercise = exerciseRepository.getById(Long.parseLong(exerciseId));
        Set<Workout> workouts = exercise.getWorkouts();

        for (int i : workoutIds){
            Workout workout = workoutRepository.getById(Long.parseLong(String.valueOf(i)));
            workouts.add(workout);
            workout.getExercises().add(exercise);
        }
        exercise.setWorkouts(workouts);

        return exerciseRepository.save(exercise);
    }

    //Function to delete the exercise with nested data inside.
    @Override
    public void deleteExercise(Exercise exercise) {

        MuscleGroup muscleGroup = exercise.getMuscleGroup();
        muscleGroup.getExercises().remove(exercise);

        exerciseRepository.delete(exercise);
    }
}
