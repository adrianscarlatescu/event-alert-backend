package com.as.eventalertbackend.data.reopsitory;

import com.as.eventalertbackend.data.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    private static final String EMAIL = "test@test.com";

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setEmail(EMAIL);

        repository.save(user);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void shouldFindByEmail() {
        // given
        // when
        User user = repository.findByEmail(EMAIL).orElse(null);

        // then
        assertNotNull(user);
        assertNotNull(user.getId());
    }

    @Test
    public void shouldNotFindByEmail() {
        // given
        // when
        User user = repository.findByEmail("random@email.com").orElse(null);

        // then
        assertNull(user);
    }

    @Test
    public void shouldExistByEmail() {
        // given
        // when
        boolean isUser = repository.existsByEmail(EMAIL);

        // then
        assertTrue(isUser);
    }

    @Test
    public void shouldNotExistByEmail() {
        // given
        // when
        boolean isUser = repository.existsByEmail("random@email.com");

        // then
        assertFalse(isUser);
    }

}
