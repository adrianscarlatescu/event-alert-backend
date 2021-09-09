package com.as.eventalertbackend.service;

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
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    public void shouldFindById() {
        // given
        Long id = 1L;
        User mockUser = new User();
        mockUser.setId(id);

        given(repository.findById(id)).willReturn(Optional.of(mockUser));

        // when
        User user = service.findById(id);

        // then
        assertNotNull(user);
        assertEquals(id, user.getId());
    }

    @Test
    public void shouldNotFindById() {
        // given
        given(repository.findById(any())).willThrow(RecordNotFoundException.class);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> service.findById(any()));
    }

    @Test
    public void shouldFindByEmail() {
        // given
        Long id = 1L;
        String email = "test@test.com";

        User mockUser = new User();
        mockUser.setId(id);
        mockUser.setEmail(email);

        given(repository.findByEmail(email)).willReturn(Optional.of(mockUser));

        // when
        User user = service.findByEmail(email);

        // then
        assertNotNull(user);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void shouldNotFindByEmail() {
        // given
        given(repository.findByEmail(any())).willThrow(RecordNotFoundException.class);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> service.findByEmail(any()));
    }

    @Test
    public void shouldSave() {
        // given
        Long id = 1L;
        User mockUser = new User();
        mockUser.setId(id);

        given(repository.save(mockUser)).willReturn(mockUser);

        // when
        User user = service.save(mockUser);

        // then
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(argumentCaptor.capture());

        User capturedUser = argumentCaptor.getValue();

        assertEquals(mockUser, capturedUser);
        assertNotNull(user);
        assertEquals(id, user.getId());
    }

    @Test
    public void shouldUpdateById() {
        // given
        Long id = 1L;
        String updatedFirstName = "updatedFirstName";
        String updatedLastName = "updatedLastName";
        LocalDate updatedDateOfBirth = LocalDate.of(2000, Month.SEPTEMBER, 10);
        String updatedPhoneNumber = "0777555333";
        String updatedImagePath = "/img/user_1.png";
        Gender updatedGender = Gender.MALE;

        UserRole mockUserRole = new UserRole();
        mockUserRole.setName(Role.ROLE_USER);

        Set<UserRole> mockUserRoles = Collections.singleton(mockUserRole);

        User mockUser = new User();
        mockUser.setId(id);
        mockUser.setUserRoles(mockUserRoles);
        mockUser.setFirstName(updatedFirstName);
        mockUser.setLastName(updatedLastName);
        mockUser.setDateOfBirth(updatedDateOfBirth);
        mockUser.setPhoneNumber(updatedPhoneNumber);
        mockUser.setImagePath(updatedImagePath);
        mockUser.setGender(updatedGender);

        User mockDbObjUser = new User();
        mockDbObjUser.setId(id);

        given(repository.findById(id)).willReturn(Optional.of(mockDbObjUser));
        given(repository.save(mockDbObjUser)).willReturn(mockDbObjUser);

        // when
        User user = service.updateById(mockUser, id);

        // then
        assertNotNull(user);
        assertEquals(id, user.getId());
        assertEquals(updatedFirstName, user.getFirstName());
        assertEquals(updatedLastName, user.getLastName());
        assertEquals(updatedDateOfBirth, user.getDateOfBirth());
        assertEquals(updatedPhoneNumber, user.getPhoneNumber());
        assertEquals(updatedImagePath, user.getImagePath());
        assertEquals(updatedGender, user.getGender());

    }

    @Test
    public void shouldDeleteById() {
        // given
        Long id = 1L;
        given(repository.existsById(id)).willReturn(true);

        // when
        service.deleteById(id);

        // then
        verify(repository).deleteById(id);
    }

    @Test
    public void shouldNotDeleteById() {
        // given
        Long id = 1L;
        given(repository.existsById(id)).willReturn(false);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> service.deleteById(id));
        verify(repository, times(0)).deleteById(id);
    }

}
