package com.StackOverflow.StackOverflow.controller;

import com.StackOverflow.StackOverflow.dto.VoteRequest;
import com.StackOverflow.StackOverflow.mapper.VoteMapper;
import com.StackOverflow.StackOverflow.service.VoteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = VoteController.class)
public class VoteControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private VoteService voteService;
    @MockBean
    private VoteMapper voteMapper;


    @Test
    public void AddNewVote() throws Exception {

        VoteRequest request = new VoteRequest(2,1,2);
        String expectedResultMessage = "Success";
        when(voteService.AddVote(any())).thenReturn(expectedResultMessage);

        mockMvc.perform(post("/vote/add")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
