package com.as.eventalertbackend.service;

import com.as.eventalertbackend.controller.request.UserRequestDto;
import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.data.model.UserRole;
import com.as.eventalertbackend.data.reopsitory.UserRepository;
import com.as.eventalertbackend.enums.Gender;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleService userRoleService;

    @InjectMocks
    private UserService userService;

    private final Long userId = 1L;
    private final String userEmail = "test@test.com";
    private final String userFirstName = "firstName";
    private final String userLastName = "lastName";
    private final LocalDate userDateOfBirth = LocalDate.of(2000, Month.SEPTEMBER, 10);
    private final String userPhoneNumber = "+40777555333";
    private final String userImagePath = "/img/user_1.png";
    private final Gender userGender = Gender.MALE;
    private final Set<Role> userRoles = Collections.singleton(Role.ROLE_USER);

    private final Long userRoleId = 1L;

    @Test
    public void shouldFindById() {
        // given
        User mockUser = new User();
        mockUser.setId(userId);

        given(userRepository.findById(userId)).willReturn(Optional.of(mockUser));

        // when
        User user = userService.findById(userId);

        // then
        assertNotNull(user);
        assertEquals(userId, user.getId());
    }

    @Test
    public void shouldNotFindById() {
        // given
        given(userRepository.findById(userId)).willThrow(RecordNotFoundException.class);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> userService.findById(userId));
    }

    @Test
    public void shouldFindByEmail() {
        // given
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setEmail(userEmail);

        given(userRepository.findByEmail(userEmail)).willReturn(Optional.of(mockUser));

        // when
        User user = userService.findByEmail(userEmail);

        // then
        assertNotNull(user);
        assertEquals(userEmail, user.getEmail());
    }

    @Test
    public void shouldNotFindByEmail() {
        // given
        given(userRepository.findByEmail(userEmail)).willThrow(RecordNotFoundException.class);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> userService.findByEmail(userEmail));
    }

    @Test
    public void shouldSave() {
        // given
        User mockUser = new User();
        mockUser.setId(userId);

        given(userRepository.save(any())).willReturn(mockUser);

        // when
        User user = userService.save(mockUser);

        // then
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());

        User capturedUser = argumentCaptor.getValue();

        assertEquals(mockUser.getId().longValue(), capturedUser.getId().longValue());
        assertNotNull(user);
    }

    @Test
    public void shouldUpdateById() {
        // given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName(userFirstName);
        userRequestDto.setLastName(userLastName);
        userRequestDto.setDateOfBirth(userDateOfBirth);
        userRequestDto.setPhoneNumber(userPhoneNumber);
        userRequestDto.setImagePath(userImagePath);
        userRequestDto.setGender(userGender);
        userRequestDto.setRoles(userRoles);

        User mockUser = new User();
        mockUser.setId(userId);

        UserRole mockUserRole = new UserRole();
        mockUserRole.setName(Role.ROLE_USER);

        given(userRepository.findById(userId)).willReturn(Optional.of(mockUser));
        given(userRoleService.findAllByName(userRoles)).willReturn(Collections.singleton(mockUserRole));
        given(userRepository.save(mockUser)).willReturn(mockUser);

        // when
        User user = userService.updateById(userRequestDto, userId);

        // then
        assertNotNull(user);
        assertEquals(userId, user.getId());
        assertEquals(userFirstName, user.getFirstName());
        assertEquals(userLastName, user.getLastName());
        assertEquals(userDateOfBirth, user.getDateOfBirth());
        assertEquals(userPhoneNumber, user.getPhoneNumber());
        assertEquals(userImagePath, user.getImagePath());
        assertEquals(userGender, user.getGender());
        assertTrue(user.getUserRoles().stream().anyMatch(userRole -> userRole.getName() == Role.ROLE_USER));
    }

    @Test
    public void shouldDeleteById() {
        // given
        given(userRepository.existsById(userId)).willReturn(true);

        // when
        userService.deleteById(userId);

        // then
        verify(userRepository).deleteById(userId);
    }

    @Test
    public void shouldNotDeleteById() {
        // given
        given(userRepository.existsById(userId)).willReturn(false);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> userService.deleteById(userId));
        verify(userRepository, times(0)).deleteById(userId);
    }

}
