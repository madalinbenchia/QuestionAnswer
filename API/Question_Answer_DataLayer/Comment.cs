using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Text;

namespace Question_Answer_DataLayer
{
    public class Comment
    {
        #region Variables
        private int id;
        private DateTime creationDate;
        private int postId;
        private int score;
        private string text;
        private int userId;
        #endregion

        #region Properties
        public int Id { get => id; set => id = value; }
        public DateTime CreationDate { get => creationDate; set => creationDate = value; }
        public int PostId { get => postId; set => postId = value; }
        public int Score { get => score; set => score = value; }
        public string Text { get => text; set => text = value; }
        public int UserId { get => userId; set => userId = value; }
        #endregion

        #region Constructor
        public Comment()
        {
            Id = 0;
            CreationDate = DateTime.Now;
            PostId = -1;
            Score = 0;
            Text = string.Empty;
            UserId = -1;
        }
        #endregion

        #region Methods
        public List<Comment> GetComments(string connectionString, int postId)
        {
            using(SqlConnection conn = new SqlConnection(connectionString))
            {
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Can not establish a connection with the database");
                }

                string sqlStatement = "SELECT * FROM Comments WHERE PostId = " + Convert.ToString(postId) + " ORDER BY Score desc";
                SqlCommand command = new SqlCommand(sqlStatement, conn);
                command.CommandType = System.Data.CommandType.Text;
                List<Comment> result = new List<Comment>();
                using(SqlDataReader reader = command.ExecuteReader()) 
                {
                    while(reader.Read()) 
                    {
                        Comment tempComment = ConvertReaderToCommentObject(reader);
                        result.Add(tempComment);

                    }
                }

                return result;
            }
        }

        public Comment AddComment(string connectionString, Comment comment)
        {
            if (string.IsNullOrEmpty(comment.Text) || comment.Text == " ")
                throw new Exception("Comment text should not be null or empty.");

            if (comment.UserId < 0)
                throw new Exception("UserId specified doesn't exist.");

            if (comment.PostId < 0)
                throw new Exception("PostId must be a valid Question or Answer Id.");

            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                Comment result = new Comment();
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Could not establish a connection with the database");
                }

                SqlCommand command = new SqlCommand("sp_AddComment", conn);
                command.CommandType = System.Data.CommandType.StoredProcedure;
                command.Parameters.Add(new SqlParameter("@Text", comment.Text));
                command.Parameters.Add(new SqlParameter("@PostId", comment.PostId));
                command.Parameters.Add(new SqlParameter("@UserId", comment.UserId));
                command.Parameters.Add(new SqlParameter("@CreationDate", comment.CreationDate));
                using (SqlDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                        result = ConvertReaderToCommentObject(reader);
                }

                return result;
            }
        }

        public string DeleteComment(string connectionString, int commentId)
        {
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                try
                {
                    conn.Open();
                }
                catch (Exception ex)
                {
                    throw new Exception(ex.Message);
                }

                SqlCommand command = new SqlCommand("sp_DeleteComment", conn);
                command.CommandType = System.Data.CommandType.StoredProcedure;
                command.Parameters.Add(new SqlParameter("@CommentId", commentId));
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

        public Comment UpdateComment(string connectionString, Comment comment)
        {
            if (string.IsNullOrEmpty(comment.Text) || comment.Text == " ")
                throw new Exception("Comment text should not be null or empty.");

            if (comment.UserId < 0)
                throw new Exception("UserId specified doesn't exist.");

            if (comment.PostId < 0)
                throw new Exception("PostId must be a valid Question or Answer Id.");

            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                Comment result = new Comment();
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Could not establish a connection with the database");
                }

                SqlCommand command = new SqlCommand("sp_UpdateComment", conn);
                command.CommandType = System.Data.CommandType.StoredProcedure;
                command.Parameters.Add(new SqlParameter("@Id", comment.Id));
                command.Parameters.Add(new SqlParameter("@Text", comment.Text));

                using (SqlDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                        result = ConvertReaderToCommentObject(reader);
                }

                return result;
            }
        }
        #endregion

        #region Utilities
        public Comment ConvertReaderToCommentObject(SqlDataReader reader)
        {
            Comment tempComment = new Comment();
            if (!reader.IsDBNull(reader.GetOrdinal("Id")))
                tempComment.Id = reader.GetInt32(reader.GetOrdinal("Id"));
            if (!reader.IsDBNull(reader.GetOrdinal("CreationDate")))
                tempComment.CreationDate = reader.GetDateTime(reader.GetOrdinal("CreationDate"));
            if (!reader.IsDBNull(reader.GetOrdinal("PostId")))
                tempComment.PostId = reader.GetInt32(reader.GetOrdinal("PostId"));
            if (!reader.IsDBNull(reader.GetOrdinal("Score")))
                tempComment.Score = reader.GetInt32(reader.GetOrdinal("Score"));
            if (!reader.IsDBNull(reader.GetOrdinal("Text")))
                tempComment.Text = reader.GetString(reader.GetOrdinal("Text"));
            if (!reader.IsDBNull(reader.GetOrdinal("UserId")))
                tempComment.UserId = reader.GetInt32(reader.GetOrdinal("UserId"));

            return tempComment;
        }
        #endregion
    }
}
