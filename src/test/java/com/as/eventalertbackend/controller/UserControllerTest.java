package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.enums.Gender;
import com.as.eventalertbackend.enums.Role;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractControllerTest {

    private static final String USERS_PATH = "/users";
    private static final String PROFILE_PATH = "/profile";

    private final Long id = 1L;
    private final String email = "test@test.com";
    private final String joinDateTime = "2020-06-20T14:30:00";
    private final String dateOfBirth = "2000-03-10";
    private final String firstName = "firstName";
    private final String lastName = "lastName";
    private final String phoneNumber = "0777555333";
    private final String imagePath = "img/user_1.png";
    private final String gender = "MALE";
    private final int reportsNumber = 0;

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
                .andExpect(jsonPath("$[0].joinDateTime", is(joinDateTime)))
                .andExpect(jsonPath("$[0].dateOfBirth", is(dateOfBirth)))
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
                .andExpect(jsonPath("$.joinDateTime", is(joinDateTime)))
                .andExpect(jsonPath("$.dateOfBirth", is(dateOfBirth)))
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

    @Test
    public void shouldGetProfile() throws Exception {
        // given
        given(userService.findById(authUser.getId())).willReturn(authUser);

        // when
        // then
        mockMvc.perform(get(PROFILE_PATH).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(authUser.getId().intValue())))
                .andExpect(jsonPath("$.email", is(authUser.getEmail())));
    }

    @Test
    public void shouldUpdateProfile() throws Exception {
        // given
        User user = new User();
        user.setId(authUser.getId());
        user.setEmail(authUser.getEmail());
        user.setUserRoles(authUser.getUserRoles());
        user.setFirstName("firstName");
        user.setLastName("lastName");

        String jsonBody = objectMapper.writeValueAsString(user);

        given(userService.updateById(any(), anyLong())).willReturn(user);

        // when
        // then
        mockMvc.perform(put(PROFILE_PATH).headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(authUser.getId().intValue())))
                .andExpect(jsonPath("$.email", is(authUser.getEmail())))
                .andExpect(jsonPath("$.userRoles[0].name", is(Role.ROLE_ADMIN.name())))
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")));
    }

    private User getMockUser() {
        User user = new User();
        user.setId(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        user.setImagePath(imagePath);
        user.setJoinDateTime(LocalDateTime.of(2020, Month.JUNE, 20, 14, 30, 0));
        user.setDateOfBirth(LocalDate.of(2000, Month.MARCH, 10));
        user.setGender(Gender.MALE);
        return user;
    }

}
