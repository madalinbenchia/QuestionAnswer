using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Text;

namespace Question_Answer.Models
{
    public class Vote
    {
        public Question_Answer_DataLayer.Vote voteDataLayerObject;
        #region Variables
        private int postId;
        private int userId;
        private int voteTypeId;
        #endregion

        #region Properties
        public int PostId { get => postId; set => postId = value; }
        public int UserId { get => userId; set => userId = value; }
        public int VoteTypeId { get => voteTypeId; set => voteTypeId = value; }
        #endregion

        public Vote()
        {
            voteDataLayerObject = new Question_Answer_DataLayer.Vote();
        }


        public int GetAlllUpVotesForAnUser(string connectionString, int postId)
        {
            try
            {
                return voteDataLayerObject.GetUpVotesForAPost(connectionString, postId);
            }catch(Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public int GetAllDownVotesForAnUser(string connectionString, int postId)
        {
            try
            {
                return voteDataLayerObject.GetDownVotesForAPost(connectionString, postId);
            }catch(Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

    }
}