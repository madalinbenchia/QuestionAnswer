using Question_Answer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Question_Answer.Mapper
{
    public class VoteMapper
    {
        public Question_Answer_DataLayer.Vote VoteServiceToVoteDataLayer(Vote vote) { 
            return new Question_Answer_DataLayer.Vote(vote.PostId, vote.UserId, vote.VoteTypeId);
        }
    }
}