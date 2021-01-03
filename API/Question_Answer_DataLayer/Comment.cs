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

                        result.Add(tempComment);

                    }
                }

                return result;
            }
        }
        #endregion
    }
}
