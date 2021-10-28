package com.experis.de.MeFit.controllers;

import com.experis.de.MeFit.models.Profile;
import com.experis.de.MeFit.models.Program;
import com.experis.de.MeFit.models.User;
import com.experis.de.MeFit.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<Void> getUser(@AuthenticationPrincipal Jwt principal) {

        String userEmail = principal.getClaimAsString("email");

        if (userService.checkIfUserExists(userEmail)) {
            User user = userService.getUserByEmail(userEmail);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(user.getId())
                    .toUri();

            return ResponseEntity.status(303).location(location).build();
        }
        else return ResponseEntity.notFound().build();
    }

    //get user by id
    @Operation(summary = "Get a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User by id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "No user found",
                    content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value="id") long userId) {

        HttpStatus status;
        User user = userService.getUserById(userId);

        status = (user != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(user, status);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User userUpdate,
                                        @PathVariable("id") long userId) {

        User user = userService.getUserById(userId);
        if (user != null) {
            user.setLastName(userUpdate.getLastName());
            user.setFirstName(userUpdate.getFirstName());

            userService.updateUser(user);
            return ResponseEntity.ok("User data updated.");
        }
        else return ResponseEntity.notFound().build();
    }

    //create new user
    @Operation(summary = "Create user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User saved",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occurred",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<User> addNewUser(@AuthenticationPrincipal Jwt principal)
    {
        // if user exists user creation
        if(userService.checkIfUserExists(principal.getClaimAsString("email")))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

        return ResponseEntity.ok(userService.createNewUserProfileFromJWT(principal));
    }
    /*
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<User>(userRepository.save(user), HttpStatus.CREATED);
    }
    */

    //delete user
    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "An error occurred",
                    content = @Content) })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") long userId){

        if (this.userService.deleleUserById(userId)) {
            return ResponseEntity.ok("User deleted.");
        }
        else return ResponseEntity.notFound().build();
    }

    //get all users with contributor requests
    @Operation(summary = "Get all users with contributor requests")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users with contributor requests",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = User.class)) }),
            @ApiResponse(responseCode = "404", description = "No users found",
                    content = @Content) })
    @GetMapping(path = "/contributorRequests")
    public List<User> getUsersWithContributorRequests() {
        return this.userService.getUserWithContributorRequests();
    }

    //set contributor request for user
    @Operation(summary = "Grant contributor request for user")
    @PutMapping("/{id}/contributorRequests/grant")
    public ResponseEntity<?> setContributorRequests(@PathVariable(value = "id") long userId){
        this.userService.setContributorRequests(userId, false);

        return ResponseEntity.ok().build();
    }

}
