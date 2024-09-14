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
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserRoleServiceTest {

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserRoleService userRoleService;

    private final Long userRoleId = 1L;
    private final Role userRoleName = Role.ROLE_USER;

    @Test
    public void shouldFindByName() {
        // given
        UserRole mockUserRole = new UserRole();
        mockUserRole.setId(userRoleId);
        mockUserRole.setName(userRoleName);

        given(userRoleRepository.findByName(userRoleName)).willReturn(Optional.of(mockUserRole));

        // when
        UserRole userRole = userRoleService.findByName(userRoleName);

        // then
        assertNotNull(userRole);
        assertEquals(userRoleId, userRole.getId());
        assertEquals(userRoleName, userRole.getName());
    }

    @Test
    public void shouldNotFindByName() {
        // given
        given(userRoleRepository.findByName(userRoleName)).willThrow(RecordNotFoundException.class);

        // when
        // then
        assertThrows(RecordNotFoundException.class, () -> userRoleService.findByName(userRoleName));
    }

}
