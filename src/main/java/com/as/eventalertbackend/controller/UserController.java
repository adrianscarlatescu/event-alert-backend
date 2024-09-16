package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.dto.request.UserRequestDto;
import com.as.eventalertbackend.dto.response.UserDto;
import com.as.eventalertbackend.jpa.entity.User;
import com.as.eventalertbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateById(@Valid @RequestBody UserRequestDto userRequestDto,
                                              @PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.updateById(userRequestDto, id));
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile() {
        return ResponseEntity.ok(userService.findById(getPrincipalId()));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDto> updateProfile(@Valid @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.updateById(userRequestDto, getPrincipalId()));
    }

    private Long getPrincipalId() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    }

}
