using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Text;

namespace Question_Answer.Models
{
    public class Vote
    {
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

        #region Methods
        public string AddVote(string connectionString, Vote vote)
        {
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Unable to establish a connection with the database");
                }

                SqlCommand command = new SqlCommand("sp_AddVote", conn);
                command.CommandType = System.Data.CommandType.StoredProcedure;
                command.Parameters.Add(new SqlParameter("@PostId", vote.PostId));
                command.Parameters.Add(new SqlParameter("@UserId", vote.UserId));
                command.Parameters.Add(new SqlParameter("@VoteType", vote.VoteTypeId));
                try
                {
                    command.ExecuteNonQuery();
                    return "Success";
                }
                catch (Exception ex)
                {
                    throw new Exception(ex.Message);
                }

            }
        }
        #endregion

    }
}