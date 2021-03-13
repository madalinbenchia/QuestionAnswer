package com.StackOverflow.StackOverflow.controller;

import com.StackOverflow.StackOverflow.dto.user.AccountRequest;
import com.StackOverflow.StackOverflow.dto.user.UserRequest;
import com.StackOverflow.StackOverflow.mapper.UserMapper;
import com.StackOverflow.StackOverflow.model.User;
import com.StackOverflow.StackOverflow.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
public class UserControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;

    @Test
    public void AddNewUser() throws Exception {

        UserRequest userRequest = new UserRequest("bmadalin","p@ss1", "I'm a student",23,"Madalin Benchia","Romania");

        when(userService.AddUser(any())).thenReturn(new User("bmadalin","p@ss1", "I'm a student",23,"Madalin Benchia","Romania"));

        mockMvc.perform(post("/user/register")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.aboutMe").value(userRequest.getAboutMe()));
    }

    @Test
    public void GetUserInserted() throws Exception {
        int userId = 1;
        User expectedUser = new User("Test","Test","Test about me",30,"Test Display Name","Romania");
        when(userService.GetUserById(eq(userId))).thenReturn(expectedUser);

        mockMvc.perform(get("/user/get/"+userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(expectedUser.getUsername()));
    }

    @Test
    public void LoginDefaultUser() throws Exception {

        AccountRequest accountRequest = new AccountRequest("Test","Test");

        when(userService.GetUser(any())).thenReturn(new User("Test","Test","Test about me",30,"Test Display Name","Romania"));

        mockMvc.perform(post("/user/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(accountRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value(accountRequest.getUsername()));
    }
}
