package com.StackOverflow.StackOverflow.mapper;

import com.StackOverflow.StackOverflow.dto.VoteRequest;
import com.StackOverflow.StackOverflow.model.Vote;
import org.springframework.stereotype.Component;

@Component
public class VoteMapper {
    public Vote voteRequestToVote(VoteRequest voteRequest) {
        return new Vote(voteRequest.getPostId(),
                voteRequest.getUserId(),
                voteRequest.getVoteTypeId());
    }
}
