package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.UserRequest;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.error.exception.ResourceNotFoundException;
import com.as.eventalertbackend.persistence.entity.User;
import com.as.eventalertbackend.persistence.entity.UserRole;
import com.as.eventalertbackend.persistence.reopsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserRoleService userRoleService;
    private final FileService fileService;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserRoleService userRoleService,
                       FileService fileService) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.fileService = fileService;
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

    public User updateById(UserRequest userRequest, Long id) {
        if (userRequest.getRoles().stream()
                .noneMatch(role -> role == Role.ROLE_USER)) {
            throw new InvalidActionException(ApiErrorMessage.DEFAULT_ROLE_MANDATORY);
        }

        if (userRequest.getImagePath() != null && !fileService.imageExists(userRequest.getImagePath())) {
            throw new ResourceNotFoundException(ApiErrorMessage.IMAGE_NOT_FOUND);
        }

        User user = findById(id);
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setImagePath(userRequest.getImagePath());
        user.setGender(userRequest.getGender());

        Set<UserRole> userRoles = userRoleService.findAllByName(userRequest.getRoles());
        user.setUserRoles(userRoles);

        return user;
    }

    public void deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.USER_NOT_FOUND);
        }
    }
}
