package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.UserRequest;
import com.as.eventalertbackend.dto.response.UserResponse;
import com.as.eventalertbackend.persistence.entity.User;
import com.as.eventalertbackend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final ModelMapper mapper;
    private final UserService userService;

    @Autowired
    public UserController(ModelMapper mapper,
                          UserService userService) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(
                userService.findAll().stream()
                        .map(user -> mapper.map(user, UserResponse.class))
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.map(userService.findById(id), UserResponse.class));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponse> updateById(@Valid @RequestBody UserRequest userRequest,
                                                   @PathVariable("id") Long id) {
        return ResponseEntity.ok(mapper.map(userService.updateById(userRequest, id), UserResponse.class));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile() {
        return ResponseEntity.ok(mapper.map(userService.findById(getPrincipalId()), UserResponse.class));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(mapper.map(userService.updateById(userRequest, getPrincipalId()), UserResponse.class));
    }

    private Long getPrincipalId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

}
