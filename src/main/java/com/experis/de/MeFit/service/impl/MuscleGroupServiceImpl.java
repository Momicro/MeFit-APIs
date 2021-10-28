package com.experis.de.MeFit.service.impl;

import com.experis.de.MeFit.models.Exercise;
import com.experis.de.MeFit.models.MuscleGroup;
import com.experis.de.MeFit.repositories.ExerciseRepository;
import com.experis.de.MeFit.repositories.MuscleGroupRepository;
import com.experis.de.MeFit.service.MuscleGroupService;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class MuscleGroupServiceImpl implements MuscleGroupService {

    @Autowired
    MuscleGroupRepository muscleGroupRepository;

    @Autowired
    ExerciseRepository exerciseRepository;

    @Override
    public MuscleGroup createMuscleGroup(MuscleGroup muscleGroup) {
        return null;
    }

    @Override
    public void deleteMuscleGroup(MuscleGroup muscleGroup) {

    }

    //First concatenate the Array with the List to then add it back to the muscle group and return the updated exercise in the RestController
    @Override
    @JsonSetter
    public MuscleGroup updateMuscleGroupsExercise(int[] exerciseIds, String muscleGroupId) {
        MuscleGroup muscleGroup = muscleGroupRepository.getById(Long.valueOf(muscleGroupId));
        Set<Exercise> exercises = muscleGroup.getExercises();

        for (int i : exerciseIds){
            Exercise exercise = exerciseRepository.getById(Long.parseLong(String.valueOf(i)));
            exercises.add(exercise);
            exercise.setMuscleGroup(muscleGroup);
        }
        muscleGroup.setExercises(exercises);

        return muscleGroupRepository.save(muscleGroup);
    }

}