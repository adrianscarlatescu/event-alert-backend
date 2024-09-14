package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.controller.request.UserRequestDto;
import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.dto.UserDto;
import com.as.eventalertbackend.service.UserService;
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

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> users = userService.findAll().stream()
                .map(User::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findById(id).toDto());
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateById(@Valid @RequestBody UserRequestDto userRequestDto,
                                              @PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.updateById(userRequestDto, id).toDto());
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getProfile() {
        User principal =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userService.findById(principal.getId()).toDto());
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDto> updateProfile(@Valid @RequestBody UserRequestDto userRequestDto) {
        User principal =
                (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userService.updateById(userRequestDto, principal.getId()).toDto());
    }

}
