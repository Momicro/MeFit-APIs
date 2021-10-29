package com.experis.de.MeFit.controllers;

import com.experis.de.MeFit.models.Profile;
import com.experis.de.MeFit.repositories.ProfileRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.Console;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private byte[] blankImage = {
            0x47, 0x49, 0x46, 0x38, 0x39, 0x61, 0x01, 0x00, 0x01, 0x00, (byte)0x80, 0x00, 0x00, 0x00, 0x00, 0x00,
            (byte)0xFF, (byte)0xFF, (byte)0xFF, 0x21, (byte)0xF9, 0x04, 0x01, 0x00, 0x00, 0x00, 0x00, 0x2C, 0x00, 0x00, 0x00, 0x00,
            0x01, 0x00, 0x01, 0x00, 0x00, 0x02, 0x01, 0x44, 0x00, 0x3B
    };

    @Autowired
    ProfileRepository profileRepository;

    //get all profiles
    @Operation(summary = "Get all profiles without any filter")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of profiles",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "No profiles found",
                    content = @Content) })
    @GetMapping()
    public List<Profile> getAllProfiles(){
        return this.profileRepository.findAll();
    }

    //get profile by id
    @Operation(summary = "Get a specific profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "No profile found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<Profile> getProfileById(@PathVariable(value="id") long profileId) {

        HttpStatus status;
        if (profileRepository.existsById(profileId)) {
            return ResponseEntity.ok(profileRepository.findById(profileId).get());
        }
        else return ResponseEntity.notFound().build();
    }

    //create profile
    @Operation(summary = "Create profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile saved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<Profile> createProfile(@RequestBody Profile profile) {
        return new ResponseEntity<Profile>(profileRepository.save(profile), HttpStatus.CREATED);
    }

    //update profile
    @Operation(summary = "Update profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Profile> updateProfile(@PathVariable(value="id") long profileId,
                                                 @RequestBody Profile profileDetails) {

        if (!profileRepository.existsById(profileId))
            return ResponseEntity.notFound().build();

        Profile profile = profileRepository.getById(profileId);

        profile.setWeight(profileDetails.getWeight());
        profile.setHeight(profileDetails.getHeight());
        profile.setAge(profileDetails.getAge());
        profile.setFitnessLevel(profileDetails.getFitnessLevel());
        profile.setTrainingFrequency(profileDetails.getTrainingFrequency());
        profile.setContributorRequest(profileDetails.getContributorRequest());

        return ResponseEntity.ok(this.profileRepository.save(profile));
    }

    //delete profile
    @Operation(summary = "Delete profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable(value = "id") long profileId){

        Profile profile = profileRepository.getById(profileId);

        this.profileRepository.delete(profile);
    }

    //Delete all profiles
    @Operation(summary = "Delete all profiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All profiles deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) })
    @DeleteMapping
    public void deleteAllProfiles(){
        this.profileRepository.deleteAll();
    }

    //Upload picture
    @Operation(summary = "Upload Picture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Picture uploaded",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) } )
    @PostMapping("/{id}/picture")
    public ResponseEntity<Profile> uploadPicture(@PathVariable(value="id") long personId,
                                                 @RequestParam("file") MultipartFile multipartPicture) throws Exception {

        Profile profile = profileRepository.getById(personId);

        try {
            var picture = multipartPicture.getBytes();
            profile.setPicture(picture);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(this.profileRepository.save(profile));
    }

    //Download picture
    @Operation(summary = "Download Picture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Picture downloaded",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Profile.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occured",
                    content = @Content) } )
    @GetMapping(value = "/{id}/picture", produces = MediaType.IMAGE_JPEG_VALUE)
    Resource downloadPicture(@PathVariable(value="id") long personId) {
        byte[] picture = null;

        try {

            if (profileRepository.existsById(personId))
            {
                Profile profile = profileRepository.getById(personId);
                picture = profile.getPicture();
            }
        }
        catch (Exception ex) {
        }

        if (picture == null) // no profile picture show blank image
            picture = blankImage;

        return new ByteArrayResource(picture);
    }

}
