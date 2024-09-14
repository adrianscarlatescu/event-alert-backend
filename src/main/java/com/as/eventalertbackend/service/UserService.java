package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.UserRequestDto;
import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.data.model.UserRole;
import com.as.eventalertbackend.data.reopsitory.UserRepository;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.handler.exception.InvalidActionException;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserRoleService userRoleService;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return findByEmail(username);
        } catch (RecordNotFoundException e) {
            throw new UsernameNotFoundException(username);
        }
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException("User not found"));
    }

    public User updateById(UserRequestDto userRequestDto, Long id) {
        if (userRequestDto.getRoles().stream()
                .noneMatch(role -> role == Role.ROLE_USER)) {
            throw new InvalidActionException("The user role is mandatory");
        }

        User user = findById(id);
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setDateOfBirth(userRequestDto.getDateOfBirth());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        user.setImagePath(userRequestDto.getImagePath());
        user.setGender(userRequestDto.getGender());

        Set<UserRole> userRoles = userRoleService.findAllByName(userRequestDto.getRoles());
        user.setUserRoles(userRoles);

        return userRepository.save(user);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException("User not found");
        }
    }
}
