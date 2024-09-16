package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.request.UserRequestDto;
import com.as.eventalertbackend.dto.response.UserResponseDto;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.handler.ApiErrorMessage;
import com.as.eventalertbackend.handler.exception.InvalidActionException;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import com.as.eventalertbackend.jpa.entity.User;
import com.as.eventalertbackend.jpa.entity.UserRole;
import com.as.eventalertbackend.jpa.reopsitory.UserRepository;
import com.as.eventalertbackend.jpa.reopsitory.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll().stream()
                .map(User::toDto)
                .collect(Collectors.toList());
    }

    public UserResponseDto findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.USER_NOT_FOUND))
                .toDto();
    }

    public UserResponseDto updateById(UserRequestDto userRequestDto, Long id) {
        if (userRequestDto.getRoles().stream()
                .noneMatch(role -> role == Role.ROLE_USER)) {
            throw new InvalidActionException(ApiErrorMessage.DEFAULT_ROLE_MANDATORY);
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(ApiErrorMessage.USER_NOT_FOUND));
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setDateOfBirth(userRequestDto.getDateOfBirth());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        user.setImagePath(userRequestDto.getImagePath());
        user.setGender(userRequestDto.getGender());

        Set<UserRole> userRoles = userRoleRepository.findAllByNameIn(userRequestDto.getRoles());
        user.setUserRoles(userRoles);

        return userRepository.save(user).toDto();
    }

    public void deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.USER_NOT_FOUND);
        }
    }
}
