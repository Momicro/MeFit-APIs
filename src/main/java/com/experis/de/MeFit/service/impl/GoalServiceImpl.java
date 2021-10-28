package com.experis.de.MeFit.service.impl;

import com.experis.de.MeFit.models.Goal;
import com.experis.de.MeFit.models.GoalWorkout;
import com.experis.de.MeFit.models.Workout;
import com.experis.de.MeFit.repositories.*;
import com.experis.de.MeFit.service.GoalService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GoalServiceImpl implements GoalService {

    private GoalRepository goalRepository;
    private GoalWorkoutRepository goalWorkoutRepository;
    private WorkoutRepository workoutRepository;
    private ProgramRepository programRepository;

    @Autowired
    public GoalServiceImpl(GoalRepository goalRepository, GoalWorkoutRepository goalWorkoutRepository, WorkoutRepository workoutRepository) {
        this.goalRepository = goalRepository;
        this.goalWorkoutRepository = goalWorkoutRepository;
        this.workoutRepository = workoutRepository;
    }

    @Override
    public Goal createGoal(Goal goal) {
        return null;
    }

    @Override
    public void deleteGoal(Goal goal) {
    }

    public void setWorkout(Long goalId, Long workoutId, Boolean completed) {

        var goalWorkout = goalWorkoutRepository.get(goalId, workoutId);

        if (goalWorkout == null) {
            goalWorkout = new GoalWorkout();
            goalWorkout.setGoal(goalRepository.getById(goalId));
            goalWorkout.setWorkout(workoutRepository.getById(workoutId));
        }

        goalWorkout.setCompleted(completed);

        this.goalWorkoutRepository.save(goalWorkout);
    }

    public void setProgram(Long goalId, Long programId, Boolean completed) {

        var goal = goalRepository.getById(goalId);
        var program = programRepository.getById(programId);

        goal.getPrograms().add(program);

        for (var workout : program.getWorkouts()) {
            setWorkout(goalId, workout.getId(), completed);
        }

        this.goalRepository.save(goal);
    }

    public List<Workout> getWorkouts(Long goalId, Boolean completed) {
        return goalWorkoutRepository.get(goalId, completed);
    }
}
