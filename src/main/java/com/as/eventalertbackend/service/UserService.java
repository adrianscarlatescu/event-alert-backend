package com.as.eventalertbackend.service;

import com.as.eventalertbackend.dto.user.UserDTO;
import com.as.eventalertbackend.dto.user.UserUpdateDTO;
import com.as.eventalertbackend.error.ApiErrorMessage;
import com.as.eventalertbackend.error.exception.InvalidActionException;
import com.as.eventalertbackend.error.exception.RecordNotFoundException;
import com.as.eventalertbackend.error.exception.ResourceNotFoundException;
import com.as.eventalertbackend.model.RoleCode;
import com.as.eventalertbackend.persistence.entity.Role;
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

    private final RoleService roleService;
    private final FileService fileService;

    @Autowired
    public UserService(ModelMapper mapper,
                       UserRepository userRepository,
                       RoleService roleService,
                       FileService fileService) {
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.roleService = roleService;
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

    public UserDTO findById(Long id) {
        return mapper.map(findEntityById(id), UserDTO.class);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(user -> mapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO updateById(UserUpdateDTO userUpdateDTO, Long id) {
        User user = findEntityById(id);

        if ((userRepository.existsEventByUserId(id) || userRepository.existsCommentByUserId(id)) &&
                (userUpdateDTO.getFirstName() == null || userUpdateDTO.getLastName() == null)) {
            throw new InvalidActionException(ApiErrorMessage.PROFILE_NAME_REQUIRED);
        }
        if (userUpdateDTO.getRoleCodes().stream().noneMatch(role -> role == RoleCode.ROLE_BASIC)) {
            throw new InvalidActionException(ApiErrorMessage.DEFAULT_ROLE_REQUIRED);
        }
        if (userUpdateDTO.getImagePath() != null && !fileService.imageExists(userUpdateDTO.getImagePath())) {
            throw new ResourceNotFoundException(ApiErrorMessage.IMAGE_NOT_FOUND);
        }

        user.setFirstName(userUpdateDTO.getFirstName());
        user.setLastName(userUpdateDTO.getLastName());
        user.setDateOfBirth(userUpdateDTO.getDateOfBirth());
        user.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        user.setImagePath(userUpdateDTO.getImagePath());
        user.setGenderCode(userUpdateDTO.getGenderCode());

        List<Role> roles = roleService.findAllEntitiesByCode(userUpdateDTO.getRoleCodes());
        user.setRoles(roles);

        return mapper.map(user, UserDTO.class);
    }

    public void deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException(ApiErrorMessage.USER_NOT_FOUND);
        }
    }
}
