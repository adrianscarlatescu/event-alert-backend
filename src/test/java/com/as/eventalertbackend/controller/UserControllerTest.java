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

    @Test
    public void shouldGetAll() throws Exception {
        // given
        User user = getMockUser();
        given(userService.findAll()).willReturn(Collections.singletonList(user));

        // when
        // then
        mockMvc.perform(get("/users").headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].email", is("test@test.com")))
                .andExpect(jsonPath("$[0].joinDateTime", is("2020-06-20T14:30:00")))
                .andExpect(jsonPath("$[0].dateOfBirth", is("2000-03-10")))
                .andExpect(jsonPath("$[0].firstName", is("firstName")))
                .andExpect(jsonPath("$[0].lastName", is("lastName")))
                .andExpect(jsonPath("$[0].phoneNumber", is("0777555333")))
                .andExpect(jsonPath("$[0].imagePath", is("img/user_1.png")))
                .andExpect(jsonPath("$[0].gender", is("MALE")))
                .andExpect(jsonPath("$[0].reportsNumber", is(0)));
    }

    @Test
    public void shouldGeById() throws Exception {
        // given
        Long id = 1L;
        User user = getMockUser();
        given(userService.findById(id)).willReturn(user);

        // when
        // then
        mockMvc.perform(get("/users/{id}", id).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.email", is("test@test.com")))
                .andExpect(jsonPath("$.joinDateTime", is("2020-06-20T14:30:00")))
                .andExpect(jsonPath("$.dateOfBirth", is("2000-03-10")))
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")))
                .andExpect(jsonPath("$.phoneNumber", is("0777555333")))
                .andExpect(jsonPath("$.imagePath", is("img/user_1.png")))
                .andExpect(jsonPath("$.gender", is("MALE")))
                .andExpect(jsonPath("$.reportsNumber", is(0)));
    }

    @Test
    public void shouldDeleteById() throws Exception {
        // given
        Long id = 1L;

        // when
        // then
        mockMvc.perform(delete("/users/{id}", id).headers(httpHeaders))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetProfile() throws Exception {
        // given
        given(userService.findById(authUser.getId())).willReturn(authUser);

        // when
        // then
        mockMvc.perform(get("/profile").headers(httpHeaders))
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
        mockMvc.perform(put("/profile").headers(httpHeaders).content(jsonBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(authUser.getId().intValue())))
                .andExpect(jsonPath("$.email", is(authUser.getEmail())))
                .andExpect(jsonPath("$.userRoles[0].name", is(Role.ROLE_ADMIN.name())))
                .andExpect(jsonPath("$.firstName", is("firstName")))
                .andExpect(jsonPath("$.lastName", is("lastName")));
    }

    private User getMockUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setEmail("test@test.com");
        user.setPassword("password");
        user.setPhoneNumber("0777555333");
        user.setImagePath("img/user_1.png");
        user.setJoinDateTime(LocalDateTime.of(2020, Month.JUNE, 20, 14, 30, 0));
        user.setDateOfBirth(LocalDate.of(2000, Month.MARCH, 10));
        user.setGender(Gender.MALE);
        return user;
    }

}
