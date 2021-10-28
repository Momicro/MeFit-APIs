package com.experis.de.MeFit.controllers;

import com.experis.de.MeFit.models.Exercise;
import com.experis.de.MeFit.models.MuscleGroup;
import com.experis.de.MeFit.repositories.MuscleGroupRepository;
import com.experis.de.MeFit.service.MuscleGroupService;
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
@RequestMapping("/api/v1/muscleGroups")
public class MuscleGroupController {

    @Autowired
    MuscleGroupRepository muscleGroupRepository;

    @Autowired
    MuscleGroupService muscleGroupService;

    //get all muscleGroups
    @Operation(summary = "Get all muscle groups without any filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of muscle groups",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MuscleGroup.class)) }),
            @ApiResponse(responseCode = "404", description = "No muscle group found",
                    content = @Content) })
    @GetMapping()
    public List<MuscleGroup> getAllMuscleGroups() {
        return this.muscleGroupRepository.findAll();
    }

    //get muscle group by id
    @Operation(summary = "Get a specific muscle group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Muscle group by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MuscleGroup.class)) }),
            @ApiResponse(responseCode = "404", description = "No muscle group found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<MuscleGroup> getMuscleGroupById(@PathVariable(value="id") long muscleGroupId){

        HttpStatus status;
        MuscleGroup muscleGroup = muscleGroupRepository.findById(muscleGroupId).get();

        status = (muscleGroup != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(muscleGroup, status);
    }

    //create muscle group
    @Operation(summary = "Create muscle group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Muscle group saved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MuscleGroup.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<MuscleGroup> createMuscleGroup(@RequestBody MuscleGroup muscleGroup) {
        MuscleGroup muscleGroup1 = muscleGroupRepository.findByName(muscleGroup.getName());

        if (muscleGroup1 == null)
            return new ResponseEntity<>(muscleGroupRepository.save(muscleGroup), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //update muscle group
    @Operation(summary = "Update muscle group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Muscle group updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MuscleGroup.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PutMapping("/{id}")
    public ResponseEntity<MuscleGroup> updateMuscleGroup(@PathVariable(value="id") long muscleGroupId,
                                                         @RequestBody MuscleGroup muscleGroupDetails) {

        MuscleGroup muscleGroup = muscleGroupRepository.getById(muscleGroupId);

        muscleGroup.setName(muscleGroupDetails.getName());

        return ResponseEntity.ok(this.muscleGroupRepository.save(muscleGroup));
    }

    //delete muscle group
    @Operation(summary = "Delete muscle group")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Muscle group deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MuscleGroup.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public void deleteMuscleGroup(@PathVariable(value = "id") long muscleGroupId){

        MuscleGroup muscleGroup = muscleGroupRepository.getById(muscleGroupId);

        this.muscleGroupRepository.delete(muscleGroup);
    }

    //Delete all muscle groups
    @Operation(summary = "Delete all muscle groups")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All muscle groups deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MuscleGroup.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @DeleteMapping
    public void deleteAllMuscleGroups(){
        this.muscleGroupRepository.deleteAll();
    }

    //update muscle group exercises
    @Operation(summary = "Update muscle group exercises")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Muscle group exercises updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MuscleGroup.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PutMapping("/{id}/update/exercises")
    public ResponseEntity<MuscleGroup> updateMuscleGroupsExercise(@PathVariable(value="id") long muscleGroupId,
                                                                  @RequestBody int[] exerciseIds){

        MuscleGroup muscleGroup = muscleGroupRepository.findById(muscleGroupId).get();

        return ResponseEntity.ok(
                this.muscleGroupService.updateMuscleGroupsExercise(exerciseIds,String.valueOf(muscleGroupId)));
    }

}