package com.as.eventalertbackend.data.reopsitory;

import com.as.eventalertbackend.data.model.UserRole;
import com.as.eventalertbackend.enums.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository repository;

    @BeforeEach
    void setUp() {
        UserRole role = new UserRole();
        role.setName(Role.ROLE_USER);

        repository.save(role);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void shouldFindByName() {
        // given
        // when
        UserRole role = repository.findByName(Role.ROLE_USER).orElse(null);

        // then
        assertNotNull(role);
        assertNotNull(role.getName());
    }

    @Test
    public void shouldNotFindByName() {
        // given
        // when
        UserRole role = repository.findByName(Role.ROLE_ADMIN).orElse(null);

        // then
        assertNull(role);
    }

}
