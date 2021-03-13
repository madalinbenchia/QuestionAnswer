package com.StackOverflow.StackOverflow.controller;

import com.StackOverflow.StackOverflow.dto.VoteRequest;
import com.StackOverflow.StackOverflow.mapper.VoteMapper;
import com.StackOverflow.StackOverflow.service.VoteService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/vote")
@Api(value = "/vote",
        tags = "Vote Controller")
public class VoteController {
    private final VoteService voteService;
    private final VoteMapper voteMapper;

    public VoteController(VoteService voteService, VoteMapper voteMapper) {
        this.voteService = voteService;
        this.voteMapper = voteMapper;
    }

    @PostMapping("/add")
    @ApiOperation(value = "Add a new Vote for an answer",
            notes = "Add a Vote based on the information received from request")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The Vote was successfully added based on the received request"),
            @ApiResponse(code = 400, message = "Validation error on the received request"),
            @ApiResponse(code = 417, message = "Information provided in the request doesn't not meet the condition.")
    })
    public ResponseEntity<String> AddVote(
            @RequestBody
            @ApiParam(name = "voteObject", value = "vote details", required = true)
                    VoteRequest voteRequest) {
        try {
            String resultMessage = voteService.AddVote(voteMapper.voteRequestToVote(voteRequest));
            return ResponseEntity
                    .created(URI.create("/vote/add"))
                    .body(resultMessage);
        }catch(Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ex.getMessage());
        }
    }


}
