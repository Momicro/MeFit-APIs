package com.experis.de.MeFit.controllers;

import com.experis.de.MeFit.models.MuscleGroup;
import com.experis.de.MeFit.models.Workout;
import com.experis.de.MeFit.models.WorkoutExercises;
import com.experis.de.MeFit.repositories.WorkoutExercisesRepository;
import com.experis.de.MeFit.repositories.WorkoutRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/workouts")
public class WorkoutController {

    @Autowired
    WorkoutRepository workoutRepository;

    @Autowired
    WorkoutExercisesRepository workoutExercisesRepository;

    //get all workouts
    @Operation(summary = "Get all workouts without any filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of workouts",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Workout.class)) }),
            @ApiResponse(responseCode = "404", description = "No workouts found",
                    content = @Content) })
    @GetMapping()
    public List<Workout> getAllWorkouts(){
        return this.workoutRepository.findAll();
    }

    //get workouts by id
    @Operation(summary = "Get a specific workouts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Workout.class)) }),
            @ApiResponse(responseCode = "404", description = "No workouts found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable(value="id") long workoutId) {

        HttpStatus status;
        Workout workout = workoutRepository.getById(workoutId);

        status = (workout != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(workout, status);
    }

    //save workout
    @Operation(summary = "Save workouts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "workouts saved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Workout.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Workout> createWorkout(@RequestBody Workout workout) {
        Workout workout1 = workoutRepository.getById(workout.getId());

        if (workout1 == null)
            return new ResponseEntity<Workout>(workoutRepository.save(workout), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //update workout
    @Operation(summary = "Update workouts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "workouts updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Workout.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<Workout> updateWorkout(@PathVariable(value="id") long workoutId,
                                                   @RequestBody Workout workoutDetails) {

        Workout workout = workoutRepository.getById(workoutId);

        workout.setName(workoutDetails.getName());
        workout.setType(workoutDetails.getType());
        workout.setExercises(workoutDetails.getExercises());


        return ResponseEntity.ok(this.workoutRepository.save(workout));
    }

    //delete workout
    @Operation(summary = "Delete workouts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "workouts deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Workout.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public void deleteWorkout(@PathVariable(value = "id") long workoutId){

        Workout workout = workoutRepository.getById(workoutId);

        this.workoutRepository.delete(workout);
    }

    //Delete all workouts
    @Operation(summary = "Delete all workouts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All workouts deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Workout.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @DeleteMapping
    public void deleteAllWorkouts(){
        this.workoutRepository.deleteAll();
    }

    //update repetition, weight and fitnessLevel
    @Operation(summary = "Update repetition, weight and fitnessLevel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WorkoutExercises.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PutMapping("workoutExercises/update/{id}")
    public ResponseEntity<WorkoutExercises> updateWorkout(@PathVariable(value="id") long workoutExercisesId,
                                                 @RequestBody WorkoutExercises workoutExercisesDetails) {

        WorkoutExercises workoutExercises = workoutExercisesRepository.getById(workoutExercisesId);
        workoutExercises.setRepetition(workoutExercisesDetails.getRepetition());
        workoutExercises.setWeight(workoutExercisesDetails.getWeight());
        workoutExercises.setFitnessLevel(workoutExercisesDetails.getFitnessLevel());
        //workoutExercises.setExercises(workoutExercises.getExercises());

        return ResponseEntity.ok(this.workoutExercisesRepository.save(workoutExercises));
    }

    //get all workouts and exercises
    @Operation(summary = "Get all workouts and exercises without any filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of workouts",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = WorkoutExercises.class)) }),
            @ApiResponse(responseCode = "404", description = "No workouts found",
                    content = @Content) })
    @GetMapping("workoutExercises")
    public List<WorkoutExercises> getAllWorkoutExercises(){
        return this.workoutExercisesRepository.findAll();
    }
/*
    //update workout exercises
    @Operation(summary = "Update workout exercises")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workout exercises updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Workout.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PutMapping("/{id}/update/exercises")
    public ResponseEntity<Workout> updateWorkoutsExercise(@PathVariable(value="id") long workoutId,
                                                                  @RequestBody int[] exerciseIds){

        Workout workout = workoutRepository.findById(workoutId).get();

        return ResponseEntity.ok(
                this.workoutRepository.updateWorkoutsExercise(exerciseIds,String.valueOf(workoutId)));
    }

 */
}
