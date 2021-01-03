using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Text;

namespace Question_Answer_DataLayer
{
    public class Post
    {
        #region Variables
        private int id;
        private int acceptedAnswerId;
        private int answerCount;
        private string body;
        private DateTime closeDate;
        private int commentCount;
        private DateTime creationDate;
        private int favouriteCount;
        private DateTime lastActivityDate;
        private DateTime lastEditDate;
        private string lastEditorDisplayName;
        private int lastEditorUserId;
        private int ownerUserId;
        private int parentId;
        private int postTypeId;
        private int score;
        private string tags;
        private string title;
        private int viewCount;
        #endregion

        #region Properties
        public int Id { get => id; set => id = value; }
        public int AcceptedAnswerId { get => acceptedAnswerId; set => acceptedAnswerId = value; }
        public int AnswerCount { get => answerCount; set => answerCount = value; }
        public string Body { get => body; set => body = value; }
        public DateTime CloseDate { get => closeDate; set => closeDate = value; }
        public int CommentCount { get => commentCount; set => commentCount = value; }
        public DateTime CreationDate { get => creationDate; set => creationDate = value; }
        public int FavouriteCount { get => favouriteCount; set => favouriteCount = value; }
        public DateTime LastActivityDate { get => lastActivityDate; set => lastActivityDate = value; }
        public DateTime LastEditDate { get => lastEditDate; set => lastEditDate = value; }
        public string LastEditorDisplayName { get => lastEditorDisplayName; set => lastEditorDisplayName = value; }
        public int LastEditorUserId { get => lastEditorUserId; set => lastEditorUserId = value; }
        public int OwnerUserId { get => ownerUserId; set => ownerUserId = value; }
        public int ParentId { get => parentId; set => parentId = value; }
        public int PostTypeId { get => postTypeId; set => postTypeId = value; }
        public int Score { get => score; set => score = value; }
        public string Tags { get => tags; set => tags = value; }
        public string Title { get => title; set => title = value; }
        public int ViewCount { get => viewCount; set => viewCount = value; }
        #endregion

        #region Methods
        public List<Post> GetPosts(string connnectionString,string tags = null)
        {
            using(SqlConnection conn = new SqlConnection(connnectionString))
            {
                List<Post> PostsList = new List<Post>();
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Can not establish a connection with the database.");
                }

                //build the sql statement
                // if tags is null, we will filter the posts based on the tags
                //otherwise, we will not filter
                string sqlStatement = "SELECT * FROM Posts WHERE ParentId = 0 ORDER BY score DESC";

                //create the command
                SqlCommand command = new SqlCommand(sqlStatement, conn);
                command.CommandType = System.Data.CommandType.Text;
                using(SqlDataReader reader = command.ExecuteReader())
                {
                    while(reader.Read())
                    {
                        Post temp = ConnvertReaderToPostObject(reader);
                        PostsList.Add(temp);

                    }
                }

                return PostsList;
            }
        }

        public Post GetPost(string connectionString, int id)
        {
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                Post result = new Post();
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Can not establish a connection with the database.");
                }

                //build the sql statement
                // if tags is null, we will filter the posts based on the tags
                //otherwise, we will not filter
                string sqlStatement = "SELECT * FROM Posts WHERE Id = " + Convert.ToString(id);

                //create the command
                SqlCommand command = new SqlCommand(sqlStatement, conn);
                command.CommandType = System.Data.CommandType.Text;
                using (SqlDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        result = ConnvertReaderToPostObject(reader);
                        

                    }
                }

                return result;
            }
        }
        public List<Post> GetAnswers(string connectionString, int parentId)
        {
            using(SqlConnection conn = new SqlConnection(connectionString))
            {
                List<Post> AnswersList = new List<Post>();
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Can not establish a connection with the database");
                }
                List<Post> result = new List<Post>();
                string sqlStatement = "SELECT * FROM POSTS WHERE ParentId = " + Convert.ToString(parentId) + "ORDER BY Score DESC";
                SqlCommand command = new SqlCommand(sqlStatement, conn);
                command.CommandType = System.Data.CommandType.Text;
                using(SqlDataReader reader = command.ExecuteReader())
                {
                    while(reader.Read())
                    {
                        Post tempAnswer = ConnvertReaderToPostObject(reader);
                        result.Add(tempAnswer);
                    }
                }
                return result;
            }

        }
        #endregion

        #region Utilities
        public Post ConnvertReaderToPostObject(SqlDataReader reader)
        {
            Post temp = new Post();
            if (!reader.IsDBNull(reader.GetOrdinal("Id")))
                temp.Id = reader.GetInt32(reader.GetOrdinal("Id"));
            if (!reader.IsDBNull(reader.GetOrdinal("AcceptedAnswerId")))
                temp.AcceptedAnswerId = reader.GetInt32(reader.GetOrdinal("AcceptedAnswerId"));
            if (!reader.IsDBNull(reader.GetOrdinal("AnswerCount")))
                temp.AnswerCount = reader.GetInt32(reader.GetOrdinal("AnswerCount"));
            if (!reader.IsDBNull(reader.GetOrdinal("Body")))
                temp.Body = reader.GetString(reader.GetOrdinal("Body"));
            if (!reader.IsDBNull(reader.GetOrdinal("ClosedDate")))
                temp.CloseDate = reader.GetDateTime(reader.GetOrdinal("ClosedDate"));
            if (!reader.IsDBNull(reader.GetOrdinal("CommentCount")))
                temp.CommentCount = reader.GetInt32(reader.GetOrdinal("CommentCount"));
            if (!reader.IsDBNull(reader.GetOrdinal("CreationDate")))
                temp.CreationDate = reader.GetDateTime(reader.GetOrdinal("CreationDate"));
            if (!reader.IsDBNull(reader.GetOrdinal("FavoriteCount")))
                temp.FavouriteCount = reader.GetInt32(reader.GetOrdinal("FavoriteCount"));
            if (!reader.IsDBNull(reader.GetOrdinal("LastActivityDate")))
                temp.LastActivityDate = reader.GetDateTime(reader.GetOrdinal("LastActivityDate"));
            if (!reader.IsDBNull(reader.GetOrdinal("LastEditDate")))
                temp.LastEditDate = reader.GetDateTime(reader.GetOrdinal("LastEditDate"));
            if (!reader.IsDBNull(reader.GetOrdinal("LastEditorUserId")))
                temp.LastEditorUserId = reader.GetInt32(reader.GetOrdinal("LastEditorUserId"));
            if (!reader.IsDBNull(reader.GetOrdinal("OwnerUserId")))
                temp.OwnerUserId = reader.GetInt32(reader.GetOrdinal("OwnerUserId"));
            if (!reader.IsDBNull(reader.GetOrdinal("PostTypeId")))
                temp.PostTypeId = reader.GetInt32(reader.GetOrdinal("PostTypeId"));
            if (!reader.IsDBNull(reader.GetOrdinal("Score")))
                temp.Score = reader.GetInt32(reader.GetOrdinal("Score"));
            if (!reader.IsDBNull(reader.GetOrdinal("Tags")))
                temp.Tags = reader.GetString(reader.GetOrdinal("Tags"));
            if (!reader.IsDBNull(reader.GetOrdinal("Title")))
                temp.Title = reader.GetString(reader.GetOrdinal("Title"));
            if (!reader.IsDBNull(reader.GetOrdinal("ViewCount")))
                temp.ViewCount = reader.GetInt32(reader.GetOrdinal("ViewCount"));
            if (!reader.IsDBNull(reader.GetOrdinal("ParentId")))
                temp.ParentId = reader.GetInt32(reader.GetOrdinal("ParentId"));

            return temp;
        }
        #endregion
    }
}
