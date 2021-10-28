package com.experis.de.MeFit.controllers;

import com.experis.de.MeFit.models.*;
import com.experis.de.MeFit.repositories.GoalRepository;
import com.experis.de.MeFit.repositories.GoalWorkoutRepository;
import com.experis.de.MeFit.repositories.ProgramRepository;
import com.experis.de.MeFit.repositories.WorkoutRepository;
import com.experis.de.MeFit.service.GoalService;
import com.experis.de.MeFit.service.impl.GoalServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/goals")
public class GoalController {

    private GoalService goalService;
    private GoalRepository goalRepository;
    private GoalWorkoutRepository goalWorkoutRepository;
    private WorkoutRepository workoutRepository;
    private ProgramRepository programRepository;

    @Autowired
    public GoalController(GoalRepository goalRepository, GoalWorkoutRepository goalWorkoutRepository, WorkoutRepository workoutRepository,
                          GoalService goalService) {
        this.goalRepository = goalRepository;
        this.goalWorkoutRepository = goalWorkoutRepository;
        this.workoutRepository = workoutRepository;
        this.goalService = goalService;
    }


    //@Autowired
    //WorkoutRepository workoutRepository;

    //@Autowired
    //GoalWorkoutRepository goalWorkoutRepository;

    //get all goals
    @Operation(summary = "Get all goals")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of goals",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Goal.class)) }),
            @ApiResponse(responseCode = "404", description = "No goals found",
                    content = @Content) })
    @GetMapping
    public List<Goal> getAllGoals(){
        return this.goalRepository.findAll();
    }

    //get goal by id
    @Operation(summary = "Get goals by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Selected goal",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Goal.class)) }),
            @ApiResponse(responseCode = "404", description = "Goal not found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<Goal> getGoalById(@PathVariable(value="id") long goalId) {
        Goal goal = goalRepository.getById(goalId);
        return ResponseEntity.ok().body(goal);
    }

    //create goal
    @Operation(summary = "Create new goal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Goal created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Goal.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Goal> createGoal(@RequestBody Goal goal) {
        Goal goal1 = goalRepository.findByStartDate(goal.getStartDate());

        if (goal1 == null)
            return new ResponseEntity<>(goalRepository.save(goal), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //update goal
    @Operation(summary = "Update a goal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Goal updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Goal.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<Goal> updateGoal(@PathVariable(value="id") long goalId,
                                           @RequestBody Goal goalDetails) {

        Goal goal = goalRepository.getById(goalId);
        goal.setStartDate(goalDetails.getStartDate());
        goal.setEndDate(goalDetails.getEndDate());
        goal.setArchived(goalDetails.isArchived());

        return ResponseEntity.ok(this.goalRepository.save(goal));
    }

    //delete goal
    @Operation(summary = "Delete goal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Goal deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Goal.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public void deleteGoal(@PathVariable(value = "id") long goalId){

        Goal goal = goalRepository.getById(goalId);

        this.goalRepository.delete(goal);
    }

    @PutMapping("/{goalId}/workouts/{workoutId}")
    public ResponseEntity<Void> setWorkout(@PathVariable(value = "goalId") long goalId,
                           @PathVariable(value = "workoutId") long workoutId)
    {
        goalService.setWorkout(goalId, workoutId, false);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{goalId}/workouts/{workoutId}/completed")
    public ResponseEntity<Void> setWorkoutCompleted(@PathVariable(value = "goalId") long goalId,
                           @PathVariable(value = "workoutId") long workoutId)
    {
        goalService.setWorkout(goalId, workoutId, true);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{goalId}/programs/{programId}")
    public ResponseEntity<Void> setProgram(@PathVariable(value = "goalId") long goalId,
                           @PathVariable(value = "programId") long programId)
    {
        goalService.setProgram(goalId, programId, false);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{goalId}/programs/{programId}/completed")
    public ResponseEntity<Void> setProgramCompleted(@PathVariable(value = "goalId") long goalId,
                                    @PathVariable(value = "programId") long programId)
    {
        goalService.setWorkout(goalId, programId, true);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{goalId}/workouts")
    public ResponseEntity<List<Workout>> getGoalWorkouts(@PathVariable(value = "goalId") long goalId) {
        var workouts = goalService.getWorkouts(goalId, false);
        return ResponseEntity.ok(workouts);
    }

    @GetMapping("/{goalId}/workouts/completed")
    public ResponseEntity<List<Workout>> getGoalWorkoutsCompleted(@PathVariable(value = "goalId") long goalId) {
        var workouts = goalService.getWorkouts(goalId, true);
        return ResponseEntity.ok(workouts);
    }

    @Operation(summary = "Update goal workouts by list of workout ID's")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Goal workouts updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Goal.class)) }),
            @ApiResponse(responseCode = "404", description = "Goal workouts could not be updated",
                    content = @Content) })
    @PutMapping(path="/{id}/workouts")
    public ResponseEntity updateGoalWorkouts(@PathVariable Long goalId, @RequestParam Long[] workoutIds) {

        if (goalRepository.existsById(goalId))
        {
            var workouts = workoutRepository.findAllById(Arrays.asList(workoutIds));
            if (workouts.size() > 0) {
                var goal = goalRepository.getById(goalId);
                goal.setWorkouts(new HashSet<>(workouts));
                goalRepository.save(goal);
                return new ResponseEntity(HttpStatus.OK);
            }
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
