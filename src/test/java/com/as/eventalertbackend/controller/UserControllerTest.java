package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.enums.Gender;
import com.as.eventalertbackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest extends AbstractControllerTest {

    @MockBean
    protected UserService userService;

    private static final String USERS_PATH = "/users";

    private final Long id = 1L;
    private final String email = "test@test.com";
    private final String firstName = "firstName";
    private final String lastName = "lastName";
    private final String phoneNumber = "0777555333";
    private final String imagePath = "img/user_1.png";
    private final String gender = "MALE";
    private final int reportsNumber = 0;
    private final LocalDateTime joinDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 14, 30, 45);
    private final LocalDate dateOfBirth = LocalDate.of(2000, Month.MARCH, 10);

    @Test
    public void shouldGetAll() throws Exception {
        // given
        User user = getMockUser();
        given(userService.findAll()).willReturn(Collections.singletonList(user));

        // when
        // then
        mockMvc.perform(get(USERS_PATH).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(id.intValue())))
                .andExpect(jsonPath("$[0].email", is(email)))
                .andExpect(jsonPath("$[0].joinDateTime", is(joinDateTime.toString())))
                .andExpect(jsonPath("$[0].dateOfBirth", is(dateOfBirth.toString())))
                .andExpect(jsonPath("$[0].firstName", is(firstName)))
                .andExpect(jsonPath("$[0].lastName", is(lastName)))
                .andExpect(jsonPath("$[0].phoneNumber", is(phoneNumber)))
                .andExpect(jsonPath("$[0].imagePath", is(imagePath)))
                .andExpect(jsonPath("$[0].gender", is(gender)))
                .andExpect(jsonPath("$[0].reportsNumber", is(reportsNumber)));
    }

    @Test
    public void shouldGeById() throws Exception {
        // given
        User user = getMockUser();
        given(userService.findById(id)).willReturn(user);

        // when
        // then
        mockMvc.perform(get(USERS_PATH + "/{id}", id).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.joinDateTime", is(joinDateTime.toString())))
                .andExpect(jsonPath("$.dateOfBirth", is(dateOfBirth.toString())))
                .andExpect(jsonPath("$.firstName", is(firstName)))
                .andExpect(jsonPath("$.lastName", is(lastName)))
                .andExpect(jsonPath("$.phoneNumber", is(phoneNumber)))
                .andExpect(jsonPath("$.imagePath", is(imagePath)))
                .andExpect(jsonPath("$.gender", is(gender)))
                .andExpect(jsonPath("$.reportsNumber", is(reportsNumber)));
    }

    @Test
    public void shouldDeleteById() throws Exception {
        mockMvc.perform(delete(USERS_PATH + "/{id}", id).headers(httpHeaders))
                .andExpect(status().isOk());
    }

    private User getMockUser() {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setImagePath(imagePath);
        user.setJoinDateTime(joinDateTime);
        user.setDateOfBirth(dateOfBirth);
        user.setGender(Gender.MALE);
        return user;
    }

}
