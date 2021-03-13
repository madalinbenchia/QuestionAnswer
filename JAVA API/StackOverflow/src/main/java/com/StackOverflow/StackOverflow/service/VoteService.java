package com.StackOverflow.StackOverflow.service;

import com.StackOverflow.StackOverflow.exception.SqlException;
import com.StackOverflow.StackOverflow.exception.post.AnswerNotFoundException;
import com.StackOverflow.StackOverflow.exception.post.UpdateScoreFailedException;
import com.StackOverflow.StackOverflow.exception.user.UserNotFoundException;
import com.StackOverflow.StackOverflow.exception.vote.WrongVoteTypeException;
import com.StackOverflow.StackOverflow.model.Answer;
import com.StackOverflow.StackOverflow.model.Question;
import com.StackOverflow.StackOverflow.model.User;
import com.StackOverflow.StackOverflow.model.Vote;
import com.StackOverflow.StackOverflow.repository.AccountRepository;
import com.StackOverflow.StackOverflow.repository.VoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class VoteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountRepository.class);

    private final VoteRepository voteRepository;
    private final UserService userService;
    private final QuestionService questionService;
    private final AnswerService answerService;

    public VoteService(VoteRepository voteRepository,UserService userService,QuestionService questionService,AnswerService answerService) {
        this.voteRepository = voteRepository;
        this.userService = userService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @Transactional
    public String AddVote(Vote vote) {
        try {
            ValidateVoteObject(vote);

            Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String strDate = dateFormat.format(date);

            vote.setCreationDate(strDate);
            String resultMessage = voteRepository.AddVote(vote);

            if(resultMessage.equals("Success")) {
                switch (vote.getVoteTypeId()) {
                    case 2:
                        resultMessage = UpdateAnswerScore(vote);
                        if(resultMessage.equals("Success"))
                            resultMessage = UpdateUserStatistics(vote);
                            break;
                    case 3:
                        resultMessage = UpdateAnswerScore(vote);
                        if(resultMessage.equals("Success"))
                            resultMessage = UpdateUserStatistics(vote);
                            break;
                    case 5:
                        resultMessage = UpdateFavoriteCount(vote);
                            break;
                    default:
                        throw new WrongVoteTypeException(vote.getVoteTypeId());
                }
            }
            return resultMessage;
        }catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void ValidateVoteObject(Vote vote) {
        //validate userId
        try {
            User userFounded = userService.GetUserById(vote.getUserId());
        }
        catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new UserNotFoundException(vote.getUserId(), ex.getMessage());
        }

        //validate postId
        try {
            Question question = questionService.GetQuestion(vote.getPostId());
            Answer answer = answerService.GetAnswer(vote.getPostId());
            if(answer == null && question == null)
                throw new AnswerNotFoundException(vote.getPostId());
        }catch(Exception ex) {
            LOGGER.error(ex.getMessage());
            throw new SqlException(ex.getMessage());
        }


    }

    @Transactional
    public String UpdateAnswerScore(Vote vote) {
        int upVotes = 0, downVotes = 0, score = 0;
        upVotes = voteRepository.GetAllVotesForPosts(vote.getPostId(),2);
        downVotes = voteRepository.GetAllVotesForPosts(vote.getPostId(), 3);
        score = upVotes - downVotes;
        String result =  answerService.UpdateScore(vote.getPostId(),score);
        if(result == "Success")
            result = UpdateQuestionTotalScore(vote);
        return result;
    }

    @Transactional
    public String UpdateFavoriteCount(Vote vote) {
        int favoriteCount = 0;
        favoriteCount = voteRepository.GetAllVotesForPosts(vote.getPostId(), 5);
        String result = questionService.UpdateFavoriteCount(vote.getPostId(),favoriteCount);
        return result;
    }

    @Transactional
    public String UpdateQuestionTotalScore(Vote vote) {
        int questionId = questionService.GetQuestionIdBasedOnAnswerId(vote.getPostId());
        int totalScore = 0;
        if(questionId != -1) {
            totalScore = questionService.CalculateQuestionTotalScore(questionId);
            String resultMessage = questionService.UpdateQuestionTotalScore(questionId,totalScore);
            return resultMessage;
        }
        else
            throw new UpdateScoreFailedException("score", questionId);
    }

    @Transactional
    public String UpdateUserStatistics(Vote vote) {
        int upVotes = voteRepository.GetAllVotesForUser(vote.getUserId(),2);
        int downVotes = voteRepository.GetAllVotesForUser(vote.getUserId(),3);
        int reputation = upVotes - downVotes;
        String resultMessage = "Success";
        resultMessage =  userService.UpdateUserUpVotes(vote.getUserId(), upVotes);
        resultMessage = userService.UpdateUserDownVotes(vote.getUserId(),downVotes);
        resultMessage = userService.UpdateUserReputation(vote.getUserId(),reputation);
        return resultMessage;
    }
}
