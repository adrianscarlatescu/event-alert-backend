package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.response.UserResponse;
import com.as.eventalertbackend.persistence.entity.User;
import com.as.eventalertbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    /*@Secured({"ADMIN"})
    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateById(@Valid @RequestBody UserRequest userRequest,
                                                   @PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.map(userService.updateById(userRequest, id), UserResponse.class));
    }*/

    @Secured({"ADMIN"})
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile() {
        Long principalId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return ResponseEntity.ok(userService.findById(principalId));
    }

    /*@PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(mapper.map(userService.updateById(userRequest, getPrincipalId()), UserResponse.class));
    }*/


}
