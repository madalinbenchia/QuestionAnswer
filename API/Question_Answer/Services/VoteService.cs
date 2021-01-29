using Question_Answer.Mapper;
using Question_Answer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Question_Answer.Services
{
    public class VoteService
    {
        public Question_Answer_DataLayer.Vote voteObject;
        public User user;
        public Question question;
        public Answer answer;
        public VoteMapper voteMapper;
        public Vote voteModel;
        public Badge badge;
        public const string successMessage = "Success";
        public VoteService()
        {
            voteObject = new Question_Answer_DataLayer.Vote();
            voteModel = new Vote();
            user = new User();
            question = new Question();
            answer = new Answer();
            voteMapper = new VoteMapper();
        }

        public string AddVote(string connectionString, Vote vote)
        {
            //Add the vote in votes table
            string resultMessage = voteObject.AddVote(connectionString, voteMapper.VoteServiceToVoteDataLayer(vote));
            if(resultMessage.Equals(successMessage))
            {
                try
                {
                    //we need to update User score
                    switch (vote.VoteTypeId)
                    {
                        case 2:
                            user.UpdateUserUpVotes(connectionString, vote.UserId);
                            break;
                        case 3:
                            user.UpdateUserDownVotes(connectionString, vote.UserId);
                            break;
                        case 5:
                            question.UpdateFavoriteCount(connectionString, vote.PostId);
                            break;
                        default: return "Invalid vote type Id";

                    }

                    //update user reputation and post score
                    //get all upvotes for a post
                    int userUpVotes = voteModel.GetAlllUpVotesForAnUser(connectionString, vote.PostId);
                    int userDownVotes = voteModel.GetAllDownVotesForAnUser(connectionString, vote.PostId);

                    //update post score
                    question.UpdateScorePost(connectionString, vote.PostId, userUpVotes, userDownVotes);

                    //update User reputation
                    int totalScoreForAllPosts = user.GetPostsTotalScoreForAUser(connectionString, vote.UserId);
                    int userReputation = user.UpdateUserReputation(connectionString, userUpVotes, userDownVotes, totalScoreForAllPosts, vote.UserId);

                    Badge badgeToBeAdded = new Badge();
                    badgeToBeAdded.UserId = vote.UserId;
                    badgeToBeAdded.Date = DateTime.Now;

                    if (userReputation >= 1900)
                        badgeToBeAdded.Name = "Diamond";
                    else if (userReputation >= 1000 && userReputation < 1900)
                        badgeToBeAdded.Name = "Platinum";
                    else if (userReputation >= 650 && userReputation < 1000)
                        badgeToBeAdded.Name = "Gold";
                    else if (userReputation >= 400 && userReputation < 600)
                        badgeToBeAdded.Name = "Silver";
                    else if (userReputation >= 150 && userReputation < 400)
                        badgeToBeAdded.Name = "Bronze";
                    else
                        badgeToBeAdded.Name = "";

                    if (badgeToBeAdded.Name != "")
                        badge.AddBadge(connectionString, badgeToBeAdded);

                    return "Success";
                }catch(Exception ex)
                {
                    throw new Exception(ex.Message);
                }
                
            }
            return resultMessage;
        }

        #region Utilities
        
        #endregion
    }
}