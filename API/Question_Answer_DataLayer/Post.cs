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
        public List<Post> GetPosts(string connnectionString, int maxNumber = 0, string tags = null)
        {
            using (SqlConnection conn = new SqlConnection(connnectionString))
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

                string sqlStatement = "";

                //if maxNumber is 0, get all questions
                //otherwise, get TOP(maxNumber) questions
                switch (maxNumber)
                {
                    case 0:
                        sqlStatement = "SELECT * FROM Posts WHERE ParentId = 0 ORDER BY score DESC";
                        break;
                    default:
                        sqlStatement = "SELECT TOP(" + maxNumber + ") * FROM Posts WHERE ParentId = 0 ORDER BY score DESC";
                        break;
                }

                //create the command
                SqlCommand command = new SqlCommand(sqlStatement, conn);
                command.CommandType = System.Data.CommandType.Text;
                using (SqlDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
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
            using (SqlConnection conn = new SqlConnection(connectionString))
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
                using (SqlDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        Post tempAnswer = ConnvertReaderToPostObject(reader);
                        result.Add(tempAnswer);
                    }
                }
                return result;
            }

        }

        public Post AddQuestion(string connectionString, Post question)
        {
            //check if requirement parameter are sent
            if (string.IsNullOrEmpty(question.Body) || question.Body == " ")
                throw new Exception("Question text should not be null or empty.");

            if (question.OwnerUserId < 0)
                throw new Exception("OwnerUserId specified doesn't exists");

            if (string.IsNullOrEmpty(question.Title) || question.Title == " ")
                throw new Exception("Question title should not be null or empty");

            //parameter checked, call th sp
            using(SqlConnection conn = new SqlConnection(connectionString))
            {
                Post result = new Post();
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Could not establish a connection with the database");
                }

                SqlCommand command = new SqlCommand("sp_AddQuestion", conn);
                command.CommandType = System.Data.CommandType.StoredProcedure;
                command.Parameters.Add(new SqlParameter("@Text", question.Body));
                command.Parameters.Add(new SqlParameter("@LastEditorUserId", question.LastEditorUserId));
                command.Parameters.Add(new SqlParameter("@LastEditorDisplayName", question.LastEditorDisplayName));
                command.Parameters.Add(new SqlParameter("@OwnerUserId", question.OwnerUserId));
                command.Parameters.Add(new SqlParameter("@Tags", question.Tags));
                command.Parameters.Add(new SqlParameter("@Title",question.Title));
                command.Parameters.Add(new SqlParameter("@CreationDate", question.CreationDate));
                command.Parameters.Add(new SqlParameter("@LastActivityDate", question.LastActivityDate));
                using(SqlDataReader reader = command.ExecuteReader())
                {
                    while(reader.Read())
                        result = ConnvertReaderToPostObject(reader);
                    
                }
                return result;
            }

        }

        public string DeleteQuestion(string connnectionString, int questionId)
        {
            using (SqlConnection conn = new SqlConnection(connnectionString))
            {
                try
                {
                    conn.Open();
                }
                catch (Exception ex)
                {
                    throw new Exception(ex.Message);
                }

                SqlCommand command = new SqlCommand("sp_DeleteQuestion", conn);
                command.CommandType = System.Data.CommandType.StoredProcedure;
                command.Parameters.Add(new SqlParameter("@QuestionId", questionId));
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

        public Post UpdateQuestion(string connectionString, Post question)
        {
            #region Validation
            //check the parameters sent
            if (question.Id < 0)
                throw new Exception("Doesn't exist a question with the specified id");

            if (String.IsNullOrEmpty(question.Body) || question.Body == " ")
                throw new Exception("Quetion should have a body");

            if (String.IsNullOrEmpty(question.LastEditorDisplayName))
                throw new Exception("User display name provided doesn't exists");

            if (question.LastEditorUserId < 0)
                throw new Exception("User specified doesn't exists");
            if (String.IsNullOrEmpty(question.Title) || question.Title == " ")
                throw new Exception("Title should contain characters.");
            if (question.ViewCount < 0)
                throw new Exception("View counter should be greater than 0");
            #endregion

            Post result = new Post();
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Could not establish a connection with the database");
                }
                SqlCommand command = new SqlCommand("sp_UpdateQuestion", conn);
                command.CommandType = System.Data.CommandType.StoredProcedure;
                if (question.AcceptedAnswerId != 0 || question.AcceptedAnswerId != null)
                    command.Parameters.Add(new SqlParameter("@AcceptedAnswerId", question.AcceptedAnswerId));
                command.Parameters.Add(new SqlParameter("@FavoriteCount",question.FavouriteCount));
                command.Parameters.Add(new SqlParameter("@ClosedDate", question.CloseDate));
                command.Parameters.Add(new SqlParameter("@Id", question.Id));
                command.Parameters.Add(new SqlParameter("@Body", question.Body));
                command.Parameters.Add(new SqlParameter("@LastActivityDate", question.LastActivityDate));
                command.Parameters.Add(new SqlParameter("@LastEditorDisplayName", question.LastEditorDisplayName));
                command.Parameters.Add(new SqlParameter("@LastEditorUserId", question.LastEditorUserId));
                command.Parameters.Add(new SqlParameter("@Tags", question.Tags));
                command.Parameters.Add(new SqlParameter("@Title", question.Title));
                command.Parameters.Add(new SqlParameter("@ViewCount", question.ViewCount));

                try
                {
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                            result = ConnvertReaderToPostObject(reader);
                    }
                    return result;
                }
                catch(Exception ex)
                {
                    throw new Exception(ex.Message);
                }
                
            }
        }

        public Post AddAnswer(string connectionString, Post answer)
        {
            //check if requirement parameter are sent
            if (string.IsNullOrEmpty(answer.Body) || answer.Body == " ")
                throw new Exception("Answer text should not be null or empty.");

            if (answer.OwnerUserId < 0)
                throw new Exception("OwnerUserId specified doesn't exist");

            if (answer.ParentId < 0)
                throw new Exception("ParentId must be a valid Question Id");

            //parameter checked, call the sp
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                Post result = new Post();
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Could not establish a connection with the database");
                }

                SqlCommand command = new SqlCommand("sp_AddAnswer", conn);
                command.CommandType = System.Data.CommandType.StoredProcedure;
                command.Parameters.Add(new SqlParameter("@Text", answer.Body));
                command.Parameters.Add(new SqlParameter("@LastEditorUserId", answer.LastEditorUserId));
                command.Parameters.Add(new SqlParameter("@LastEditorDisplayName", answer.LastEditorDisplayName));
                command.Parameters.Add(new SqlParameter("@OwnerUserId", answer.OwnerUserId));
                command.Parameters.Add(new SqlParameter("@ParentId", answer.ParentId));
                command.Parameters.Add(new SqlParameter("@CreationDate", answer.CreationDate));
                command.Parameters.Add(new SqlParameter("@LastActivityDate", answer.LastActivityDate));
                using (SqlDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                        result = ConnvertReaderToPostObject(reader);

                }
                return result;
            }

        }

        public string DeleteAnswer(string connnectionString, int answerId)
        {
            using (SqlConnection conn = new SqlConnection(connnectionString))
            {
                try
                {
                    conn.Open();
                }
                catch (Exception ex)
                {
                    throw new Exception(ex.Message);
                }

                SqlCommand command = new SqlCommand("sp_DeleteAnswer", conn);
                command.CommandType = System.Data.CommandType.StoredProcedure;
                command.Parameters.Add(new SqlParameter("@AnswerId", answerId));
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

        public Post UpdateAnswer(string connectionString, Post answer)
        {
            //check if requirement parameter are sent
            if (string.IsNullOrEmpty(answer.Body) || answer.Body == " ")
                throw new Exception("Answer text should not be null or empty.");

            if (answer.OwnerUserId < 0)
                throw new Exception("OwnerUserId specified doesn't exist");

            if (answer.ParentId < 0)
                throw new Exception("ParentId must be a valid Question Id");

            //parameter checked, call the sp
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                Post result = new Post();
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Could not establish a connection with the database");
                }

                SqlCommand command = new SqlCommand("sp_UpdateAnswer", conn);
                command.CommandType = System.Data.CommandType.StoredProcedure;
                command.Parameters.Add(new SqlParameter("@Id", answer.Id));
                command.Parameters.Add(new SqlParameter("@Body", answer.Body));
                command.Parameters.Add(new SqlParameter("@FavoriteCount", answer.FavouriteCount));
                command.Parameters.Add(new SqlParameter("@LastEditorDisplayName", answer.LastEditorDisplayName));
                command.Parameters.Add(new SqlParameter("@LastEditorUserId", answer.LastEditorUserId));
                command.Parameters.Add(new SqlParameter("@Tags", answer.Tags));
                command.Parameters.Add(new SqlParameter("@Title", answer.Title));
                command.Parameters.Add(new SqlParameter("@ViewCount", answer.Title));
                command.Parameters.Add(new SqlParameter("@LastActivityDate", answer.LastActivityDate));
                command.Parameters.Add(new SqlParameter("@LastEditDate", answer.LastEditDate));
                using (SqlDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                        result = ConnvertReaderToPostObject(reader);

                }
                return result;
            }
        }

        public List<Post> SearchPosts(string connectionString, string searchString = null)
        {
            using (SqlConnection conn = new SqlConnection(connectionString))
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

                string sqlStatement = "";

                if (searchString != null)
                {
                    char[] delimiterChars = { ' ', ',', ':', '\t', '\n', ';', '?', '!' };

                    string[] searchWords = searchString.Split(delimiterChars);
                    List<string> list = new List<string>(searchWords);
                    List<int> indexList = new List<int>();

                    foreach (string word in list)
                    {
                        if (word.Length <= 1 )
                        {
                            int idx = list.IndexOf(word);
                            if (idx >= 0)
                            {
                                indexList.Add(idx);
                            }
                            
                        }
                    }

                    int ctr = 0;
                    foreach (int index in indexList)
                    {
                        list.RemoveAt(index - ctr);
                        ctr++;
                    }

                    sqlStatement = "SELECT * FROM Posts WHERE ParentId=0 AND (";

                    for (int i = 0; i < list.Count; i++)
                    {
                        sqlStatement += "Tags like '%<" + list[i] + ">%' OR " +
                                "Title like '%" + list[i] + "%' ";

                        if (i != list.Count - 1)
                        {
                            sqlStatement += "OR ";
                        }
                    }

                    sqlStatement += ") ORDER BY score DESC";

                }
                else
                {
                    sqlStatement = "SELECT * FROM Posts WHERE ParentId=0 ORDER BY score DESC";
                }

                //create the command
                SqlCommand command = new SqlCommand(sqlStatement, conn);
                command.CommandType = System.Data.CommandType.Text;
                using (SqlDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        Post temp = ConnvertReaderToPostObject(reader);
                        PostsList.Add(temp);

                    }
                }

                return PostsList;
            }
        }

        public void UpdateFavoriteCount(string connectionString, int postId)
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

                string sqlStatement = "UPDATE Posts SET FavoriteCount = ISNULL(FavoriteCount, 0) + 1 WHERE Id = " + postId;
                SqlCommand command = new SqlCommand(sqlStatement, conn);
                command.CommandType = System.Data.CommandType.Text;
                try
                {
                    command.ExecuteNonQuery();
                }
                catch
                {
                    throw new Exception("Can not update the post favorteCount field.");
                }

            }
        }

        public void UpdateScorePost(string connectionString, int postId, int score)
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

                string sqlStatement = "UPDATE Posts SET Score = " + score + " WHERE Id = " + postId;
                SqlCommand command = new SqlCommand(sqlStatement, conn);
                command.CommandType = System.Data.CommandType.Text;
                try
                {
                    command.ExecuteNonQuery();
                }
                catch
                {
                    throw new Exception("Can not update the post favorteCount field.");
                }

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
            if (!reader.IsDBNull(reader.GetOrdinal("LastEditorDisplayName")))
                temp.LastEditorDisplayName = reader.GetString(reader.GetOrdinal("LastEditorDisplayName"));
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
