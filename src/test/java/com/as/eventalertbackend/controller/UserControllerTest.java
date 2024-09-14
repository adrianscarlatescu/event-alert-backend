package com.as.eventalertbackend.controller;

import com.as.eventalertbackend.data.model.User;
import com.as.eventalertbackend.data.model.UserRole;
import com.as.eventalertbackend.enums.Gender;
import com.as.eventalertbackend.enums.Role;
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

    private final Long userId = 1L;
    private final String userEmail = "test@test.com";
    private final String userFirstName = "firstName";
    private final String userLastName = "lastName";
    private final String userPhoneNumber = "0777555333";
    private final String userImagePath = "img/user_1.png";
    private final Gender userGender = Gender.MALE;
    private final Integer userReportsNumber = 0;
    private final LocalDateTime userJoinDateTime = LocalDateTime.of(2020, Month.JUNE, 20, 14, 30, 45);
    private final LocalDate userDateOfBirth = LocalDate.of(2000, Month.MARCH, 10);

    private final Long userRoleId = 1L;
    private final Role userRoleName = Role.ROLE_USER;

    @Test
    public void shouldGetAll() throws Exception {
        // given
        User user = getMockUser();
        given(userService.findAll()).willReturn(Collections.singletonList(user));

        // when
        // then
        mockMvc.perform(get(USERS_PATH).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(userId.intValue())))
                .andExpect(jsonPath("$[0].email", is(userEmail)))
                .andExpect(jsonPath("$[0].joinDateTime", is(userJoinDateTime.toString())))
                .andExpect(jsonPath("$[0].dateOfBirth", is(userDateOfBirth.toString())))
                .andExpect(jsonPath("$[0].firstName", is(userFirstName)))
                .andExpect(jsonPath("$[0].lastName", is(userLastName)))
                .andExpect(jsonPath("$[0].phoneNumber", is(userPhoneNumber)))
                .andExpect(jsonPath("$[0].imagePath", is(userImagePath)))
                .andExpect(jsonPath("$[0].gender", is(userGender.name())))
                .andExpect(jsonPath("$[0].reportsNumber", is(userReportsNumber)))
                .andExpect(jsonPath("$[0].userRoles[0].id", is(userRoleId.intValue())))
                .andExpect(jsonPath("$[0].userRoles[0].name", is(userRoleName.name())));
    }

    @Test
    public void shouldGeById() throws Exception {
        // given
        User user = getMockUser();
        given(userService.findById(userId)).willReturn(user);

        // when
        // then
        mockMvc.perform(get(USERS_PATH + "/{id}", userId).headers(httpHeaders))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userId.intValue())))
                .andExpect(jsonPath("$.email", is(userEmail)))
                .andExpect(jsonPath("$.joinDateTime", is(userJoinDateTime.toString())))
                .andExpect(jsonPath("$.dateOfBirth", is(userDateOfBirth.toString())))
                .andExpect(jsonPath("$.firstName", is(userFirstName)))
                .andExpect(jsonPath("$.lastName", is(userLastName)))
                .andExpect(jsonPath("$.phoneNumber", is(userPhoneNumber)))
                .andExpect(jsonPath("$.imagePath", is(userImagePath)))
                .andExpect(jsonPath("$.gender", is(userGender.name())))
                .andExpect(jsonPath("$.reportsNumber", is(userReportsNumber)))
                .andExpect(jsonPath("$.userRoles[0].id", is(userRoleId.intValue())))
                .andExpect(jsonPath("$.userRoles[0].name", is(userRoleName.name())));
    }

    @Test
    public void shouldDeleteById() throws Exception {
        mockMvc.perform(delete(USERS_PATH + "/{id}", userId).headers(httpHeaders))
                .andExpect(status().isOk());
    }

    private User getMockUser() {
        UserRole userRole = new UserRole();
        userRole.setId(userRoleId);
        userRole.setName(userRoleName);

        User user = new User();
        user.setId(userId);
        user.setFirstName(userFirstName);
        user.setLastName(userLastName);
        user.setEmail(userEmail);
        user.setPhoneNumber(userPhoneNumber);
        user.setImagePath(userImagePath);
        user.setJoinDateTime(userJoinDateTime);
        user.setDateOfBirth(userDateOfBirth);
        user.setGender(Gender.MALE);

        user.setUserRoles(Collections.singleton(userRole));

        return user;
    }

}
