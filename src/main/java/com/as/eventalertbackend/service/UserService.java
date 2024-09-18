package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.UserRequestDto;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.jpa.entity.User;
import com.as.eventalertbackend.jpa.entity.UserRole;
import com.as.eventalertbackend.jpa.reopsitory.UserRepository;
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
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.USER_NOT_FOUND));
    }

    public User updateById(UserRequestDto userRequestDto, Long id) {
        if (userRequestDto.getRoles().stream()
                .noneMatch(role -> role == Role.ROLE_USER)) {
            throw new InvalidActionException(ApiErrorMessage.DEFAULT_ROLE_MANDATORY);
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

    public void deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.USER_NOT_FOUND);
        }
    }
}
