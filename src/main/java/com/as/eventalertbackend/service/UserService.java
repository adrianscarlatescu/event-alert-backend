package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.response.UserResponse;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.persistence.entity.User;
import com.as.eventalertbackend.persistence.reopsitory.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final ModelMapper mapper;

    private final UserRepository userRepository;

    private final UserRoleService userRoleService;
    private final FileService fileService;

    @Autowired
    public UserService(ModelMapper mapper,
                       UserRepository userRepository,
                       UserRoleService userRoleService,
                       FileService fileService) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.fileService = fileService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    User findEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.USER_NOT_FOUND));
    }

    User saveEntity(User user) {
        return userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserResponse findById(Long id) {
        return mapper.map(findEntityById(id), UserResponse.class);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(user -> mapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    /*public User updateById(UserRequest userRequest, Long id) {
        if (userRequest.getUserRoleCodes().stream()
                .noneMatch(role -> role == UserRoleCode.BASIC)) {
            throw new InvalidActionException(ApiErrorMessage.DEFAULT_ROLE_MANDATORY);
        }

        if (userRequest.getImagePath() != null && !fileService.imageExists(userRequest.getImagePath())) {
            throw new ResourceNotFoundException(ApiErrorMessage.IMAGE_NOT_FOUND);
        }

        User user = findEntityById(id);
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        user.setImagePath(userRequest.getImagePath());
        user.setGenderCode(userRequest.getGenderCode());

        Set<UserRole> userRoles = userRoleService.findAllEntitiesByCode(userRequest.getUserRoleCodes());
        user.setUserRoles(userRoles);

        return user;
    }*/

    public void deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.USER_NOT_FOUND);
        }
    }
}
