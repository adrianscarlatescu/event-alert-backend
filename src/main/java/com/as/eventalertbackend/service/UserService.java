package com.as.eventalertbackend.service;

import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.data.reopsitory.UserRepository;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.handler.exception.IllegalActionException;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
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
        return repository.existsByEmail(email);
    }

    public Boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(
                        "No record for user " + id,
                        "User not found"));
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new RecordNotFoundException(
                        "No record for user " + email,
                        "User not found"));
    }

    public User updateById(User user, Long id) {
        if (user.getUserRoles().stream()
                .noneMatch(userRole -> userRole.getName() == Role.ROLE_USER)) {
            throw new IllegalActionException(
                    Role.ROLE_USER + " is mandatory",
                    "The user role is mandatory");
        }

        User dbObj = findById(id);
        dbObj.setFirstName(user.getFirstName());
        dbObj.setLastName(user.getLastName());
        dbObj.setDateOfBirth(user.getDateOfBirth());
        dbObj.setPhoneNumber(user.getPhoneNumber());
        dbObj.setImagePath(user.getImagePath());
        dbObj.setGender(user.getGender());

        if (dbObj.getUserRoles().stream()
                .anyMatch(userRole -> userRole.getName() == Role.ROLE_ADMIN)) {
            dbObj.setUserRoles(user.getUserRoles());
        }
        return repository.save(dbObj);
    }

    public User save(User user) {
        return repository.save(user);
    }

    public void deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RecordNotFoundException(
                    "No record for user " + id,
                    "User not found");
        }
    }
}
