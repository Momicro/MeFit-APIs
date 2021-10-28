package com.experis.de.MeFit.controllers;

import com.experis.de.MeFit.models.Exercise;
import com.experis.de.MeFit.models.MuscleGroup;
import com.experis.de.MeFit.models.Workout;
import com.experis.de.MeFit.repositories.ExerciseRepository;
import com.experis.de.MeFit.service.ExerciseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/exercises")
public class ExerciseController {

    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    ExerciseService exerciseService;

    //get all exercises
    @Operation(summary = "Get all exercises without any filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of exercises",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Exercise.class)) }),
            @ApiResponse(responseCode = "404", description = "No exercises found",
                    content = @Content) })
    @GetMapping()
    public List<Exercise> getAllExercises(){
        return this.exerciseRepository.findAll();
    }

    //get exercise by id
    @Operation(summary = "Get a specific exercise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Exercise.class)) }),
            @ApiResponse(responseCode = "404", description = "No exercise found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable(value="id") long exerciseId) {

        HttpStatus status;
        Exercise exercise = exerciseRepository.getById(exerciseId);

        status = (exercise != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(exercise, status);
    }

    //create exercise
    @Operation(summary = "Create exercise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise saved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Exercise.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {


        if (!exerciseService.checkIfExerciseExists(exercise.getName()))
            return new ResponseEntity<Exercise>(exerciseRepository.save(exercise), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }

    //update exercise
    @Operation(summary = "Update exercise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Exercise.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable(value="id") long exerciseId,
                                             @RequestBody Exercise exerciseDetails) {

        Exercise exercise = exerciseRepository.getById(exerciseId);

        exercise.setName(exerciseDetails.getName());
        exercise.setDescription(exerciseDetails.getDescription());


        return ResponseEntity.ok(this.exerciseRepository.save(exercise));
    }

    //delete exercise
    @Operation(summary = "Delete exercise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exercise deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Exercise.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public void deleteExercise(@PathVariable(value = "id") long exerciseId){

        Exercise exercise = exerciseRepository.getById(exerciseId);

        this.exerciseRepository.delete(exercise);
    }

    //Delete all exercises
    @Operation(summary = "Delete all exercises")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All exercise deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Exercise.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @DeleteMapping
    public void deleteAllExercises(){
        this.exerciseRepository.deleteAll();
    }


    //Get all the workouts of the exercise
    @Operation(summary = "Get all workouts in a specific exercise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of workouts",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Exercise.class)) }),
            @ApiResponse(responseCode = "404", description = "No workouts found",
                    content = @Content) })
    @GetMapping("/{id}/workouts")
    public Set<Workout> getAllWorkoutsOfExercise(@PathVariable(value = "id") long exerciseID) {

        HttpStatus status;
        Exercise exercise = exerciseRepository.getById(exerciseID);

        status = (exercise != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return exercise.getWorkouts();
    }

        //update workouts
        @Operation(summary = "Update exercise workouts")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Exercise workouts updated",
                        content = { @Content(mediaType = "application/json",
                                schema = @Schema(implementation = Exercise.class)) }),
                @ApiResponse(responseCode = "404", description = "An error occured",
                        content = @Content) })
        @PutMapping("/{id}/update/workouts")
        public ResponseEntity<Exercise> updateExerciseWorkouts(@PathVariable(value="id") long exerciseId,
                                                       @RequestBody int[] workoutIds) {

        Exercise exercise = exerciseRepository.getById(exerciseId);

            return ResponseEntity.ok(
                    this.exerciseService.updateExerciseWorkouts(workoutIds, String.valueOf(exerciseId)));
        }

    //Get all muscle group of an exercise
    @Operation(summary = "Get all muscle group in an exercise")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of muscle groups in an exercise",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Set.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @GetMapping("/{id}/muscleGroup")
    public MuscleGroup getMuscleGroup(@PathVariable(value="id") long exerciseID) {

        Exercise exercise = exerciseRepository.getById(exerciseID);

        return exercise.getMuscleGroup();
    }

}
