using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Text;

namespace Question_Answer_DataLayer
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

        public Vote(int postId, int userId, int voteTypeId)
        {
            this.PostId = postId;
            this.UserId = userId;
            this.VoteTypeId = voteTypeId;
        }
        public Vote() { }

        #region Methods
        public string AddVote(string connectionString, Vote vote)
        {
            using(SqlConnection conn = new SqlConnection(connectionString))
            {
                try
                {
                    conn.Open(); 
                }
                catch
                {
                    return "Unable to establish a connection with the database";
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
                }catch(Exception ex)
                {
                    return ex.Message;
                }
                
            }
        }

        public int GetUpVotesForAPost(string connectionString, int postId)
        {
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Can not establish a connection with the database.");
                }
                int result = -1;
                string sqlStatement = "SELECT COUNT(*) FROM Votes Where VoteTypeId = 2 AND Id=" + postId;
                SqlCommand command = new SqlCommand(sqlStatement, conn);
                command.CommandType = System.Data.CommandType.Text;
                try
                {
                    using(SqlDataReader reader = command.ExecuteReader())
                    {
                        while(reader.Read())
                        {
                            result = reader.GetInt32(0);
                        }
                    }
                    return result;
                }
                catch
                {
                    throw new Exception("Can not get the upVotes for postId = " + postId);
                }

            }
        }

        public int GetDownVotesForAPost(string connectionString, int postId)
        {
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Can not establish a connection with the database.");
                }
                int result = -1;
                string sqlStatement = "SELECT COUNT(*) FROM Votes Where VoteTypeId = 3 AND Id=" + postId;
                SqlCommand command = new SqlCommand(sqlStatement, conn);
                command.CommandType = System.Data.CommandType.Text;
                try
                {
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            result = reader.GetInt32(0);
                        }
                    }
                    return result;
                }
                catch
                {
                    throw new Exception("Can not get the downVotes for postId = " + postId);
                }

            }
        }

        #endregion

    }
}
