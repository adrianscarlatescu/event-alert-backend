package com.as.eventalertbackend.service;

import com.as.eventalertbackend.data.model.UserRole;
import com.as.eventalertbackend.data.reopsitory.UserRoleRepository;
import com.as.eventalertbackend.enums.Role;
import com.as.eventalertbackend.handler.exception.RecordNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceTest {

    @Mock
    private UserRoleRepository repository;

    @InjectMocks
    private UserRoleService service;

    @Test
    public void shouldFindByName() {
        // given
        Role role = Role.ROLE_USER;
        UserRole mockUserRole = new UserRole();
        mockUserRole.setName(role);

        given(repository.findByName(role)).willReturn(Optional.of(mockUserRole));

        // when
        UserRole userRole = service.findByName(role);

        // then
        assertNotNull(userRole);
        assertEquals(role, userRole.getName());
    }

    @Test
    public void shouldNotFindByName() {
        // given
        given(repository.findByName(any())).willThrow(RecordNotFoundException.class);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> service.findByName(any()));
    }

}
