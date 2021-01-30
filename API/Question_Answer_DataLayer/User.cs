using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Text;

namespace Question_Answer_DataLayer
{
    public class User 
    {
        #region Variables
        private string username;
        private string password;
        private int userId;
        private string aboutMe;
        private int age;
        private DateTime creationDate;
        private DateTime lastAccessDate;
        private string displayName;
        private int upVotes;
        private int downVotes;
        private string email;
        private int reputation;
        private int viewsNumber;
        private string location;
        private int role;
        #endregion

        #region Properties
        public int UserId { get => userId; set => userId = value; }
        public string AboutMe { get => aboutMe; set => aboutMe = value; }
        public int Age { get => age; set => age = value; }
        public DateTime CreationDate { get => creationDate; set => creationDate = value; }
        public DateTime LastAccessDate { get => lastAccessDate; set => lastAccessDate = value; }
        public string DisplayName { get => displayName; set => displayName = value; }
        public int UpVotes { get => upVotes; set => upVotes = value; }
        public int DownVotes { get => downVotes; set => downVotes = value; }
        public string Email { get => email; set => email = value; }
        public int Reputation { get => reputation; set => reputation = value; }
        public int ViewsNumber { get => viewsNumber; set => viewsNumber = value; }
        public string Username { get => username; set => username = value; }
        public string Location { get => location; set => location = value; }
        public int Role { get => role; set => role = value; }
        public string Password { get => password; set => password = value; }
        #endregion

        #region Methods
        public User Login(string connectionstring, string username, string password)
        {
            using(SqlConnection conn = new SqlConnection(connectionstring))
            {
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Error trying to establish a connection with the database.");
                }

                //build the sql code
                string sqlCommand = "Select ac.Username, ac.Password, ac.Role, u.Id as UserId, u.AboutMe, u.Age, u.CreationDate, u.DisplayName, u.DownVotes, u.EmailHash, u.LastAccessDate, u.Location, u.Reputation, u.UpVotes, u.Views, u.WebsiteUrl, u.AccountId " +
                    "from dbo.Accounts ac inner join dbo.Users u on ac.AccountId = u.AccountId " + 
                    "where ac.Username = " + "'" + username + "'" + " and ac.Password = " + "'" + password + "'";
                SqlCommand command = new SqlCommand(sqlCommand, conn);
                command.CommandType = System.Data.CommandType.Text;
                using(SqlDataReader reader = command.ExecuteReader())
                {
                    User tempObj = new User();

                    while (reader.Read())
                    {
                                                //read the info comes from db and put them in User object
                        if (!reader.IsDBNull(reader.GetOrdinal("UserName")))
                            tempObj.Username = reader.GetString(reader.GetOrdinal("UserName"));
                        
                        if (!reader.IsDBNull(reader.GetOrdinal("UserId")))
                            tempObj.UserId = reader.GetInt32(reader.GetOrdinal("UserId"));
                        
                        if (!reader.IsDBNull(reader.GetOrdinal("AboutMe")))
                            tempObj.AboutMe = reader.GetString(reader.GetOrdinal("AboutMe"));

                        if (!reader.IsDBNull(reader.GetOrdinal("Age")))
                            tempObj.Age = reader.GetInt32(reader.GetOrdinal("Age"));

                        if (!reader.IsDBNull(reader.GetOrdinal("CreationDate")))
                            tempObj.CreationDate = reader.GetDateTime(reader.GetOrdinal("CreationDate"));

                        if (!reader.IsDBNull(reader.GetOrdinal("DisplayName")))
                            tempObj.DisplayName = reader.GetString(reader.GetOrdinal("DisplayName"));

                        if (!reader.IsDBNull(reader.GetOrdinal("DownVotes")))
                            tempObj.DownVotes = reader.GetInt32(reader.GetOrdinal("DownVotes"));

                        if (!reader.IsDBNull(reader.GetOrdinal("LastAccessDate")))
                            tempObj.LastAccessDate = reader.GetDateTime(reader.GetOrdinal("LastAccessDate"));

                        if (!reader.IsDBNull(reader.GetOrdinal("Location")))
                            tempObj.Location = reader.GetString(reader.GetOrdinal("Location"));

                        if (!reader.IsDBNull(reader.GetOrdinal("Reputation")))
                            tempObj.Reputation = reader.GetInt32(reader.GetOrdinal("Reputation"));

                        if (!reader.IsDBNull(reader.GetOrdinal("UpVotes")))
                            tempObj.UpVotes = reader.GetInt32(reader.GetOrdinal("UpVotes"));

                        if (!reader.IsDBNull(reader.GetOrdinal("Views")))
                            tempObj.ViewsNumber = reader.GetInt32(reader.GetOrdinal("Views"));

                        if (!reader.IsDBNull(reader.GetOrdinal("Role")))
                            tempObj.Role = reader.GetInt32(reader.GetOrdinal("Role"));

                    }
                    return tempObj;
                }
            }
        }

        public User Register(string connectionString, User userInfo)
        {
            using(SqlConnection conn = new SqlConnection(connectionString))
            {
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Error trying to establish a connection with the database.");
                }

                SqlCommand command = new SqlCommand("AddUsers", conn);
                command.CommandType = System.Data.CommandType.StoredProcedure;
                command.Parameters.Add(new SqlParameter("@Username", userInfo.Username));
                command.Parameters.Add(new SqlParameter("@Password",userInfo.Password));
                command.Parameters.Add(new SqlParameter("@AboutMe", userInfo.AboutMe));
                command.Parameters.Add(new SqlParameter("@Age", userInfo.Age));
                command.Parameters.Add(new SqlParameter("@DisplayName", userInfo.DisplayName));
                command.Parameters.Add(new SqlParameter("@Location", userInfo.Location));
                using(SqlDataReader reader = command.ExecuteReader())
                {
                    User userResult = new User();
                    while (reader.Read())
                    {
                        
                        if (!reader.IsDBNull(reader.GetOrdinal("Username")))
                            userResult.Username = reader.GetString(reader.GetOrdinal("Username"));

                        if (!reader.IsDBNull(reader.GetOrdinal("Role")))
                            userResult.Role = reader.GetInt32(reader.GetOrdinal("Role"));

                        if (!reader.IsDBNull(reader.GetOrdinal("UserId")))
                            userResult.UserId = reader.GetInt32(reader.GetOrdinal("UserId"));

                        if (!reader.IsDBNull(reader.GetOrdinal("AboutMe")))
                            userResult.AboutMe = reader.GetString(reader.GetOrdinal("AboutMe"));

                        if (!reader.IsDBNull(reader.GetOrdinal("Age")))
                            userResult.Age = reader.GetInt32(reader.GetOrdinal("Age"));

                        if (!reader.IsDBNull(reader.GetOrdinal("CreationDate")))
                            userResult.CreationDate = reader.GetDateTime(reader.GetOrdinal("CreationDate"));

                        if (!reader.IsDBNull(reader.GetOrdinal("DisplayName")))
                            userResult.DisplayName = reader.GetString(reader.GetOrdinal("DisplayName"));

                        if (!reader.IsDBNull(reader.GetOrdinal("DownVotes")))
                            userResult.DownVotes = reader.GetInt32(reader.GetOrdinal("DownVotes"));

                        if (!reader.IsDBNull(reader.GetOrdinal("LastAccessDate")))
                            userResult.LastAccessDate = reader.GetDateTime(reader.GetOrdinal("LastAccessDate"));

                        if (!reader.IsDBNull(reader.GetOrdinal("Location")))
                            userResult.Location = reader.GetString(reader.GetOrdinal("Location"));

                        if (!reader.IsDBNull(reader.GetOrdinal("Reputation")))
                            userResult.Reputation = reader.GetInt32(reader.GetOrdinal("Reputation"));

                        if (!reader.IsDBNull(reader.GetOrdinal("UpVotes")))
                            userResult.UpVotes = reader.GetInt32(reader.GetOrdinal("UpVotes"));

                        if (!reader.IsDBNull(reader.GetOrdinal("Views")))
                            userResult.ViewsNumber = reader.GetInt32(reader.GetOrdinal("Views"));

                    }
                    return userResult;
                }
            }
        }

        public List<User> GetUsers(string connectionString, int maxNumber = 0)
        {
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                List<User> usersList = new List<User>();
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Can not establish a connection with the database.");
                }

                //build the sql statement
                string sqlStatement = "";

                //if maxNumber is 0, get all users ordered by reputation desc
                //otherwise, get TOP(maxNumber) users ordered by reputation desc
                switch (maxNumber)
                {
                    case 0:
                        sqlStatement = "SELECT * FROM Users ORDER BY reputation DESC";
                        break;
                    default:
                        sqlStatement = "SELECT TOP(" + maxNumber + ") * FROM Users ORDER BY reputation DESC";
                        break;
                }

                //create the command
                SqlCommand command = new SqlCommand(sqlStatement, conn);
                command.CommandType = System.Data.CommandType.Text;
                using (SqlDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        User temp = ConvertReaderToUserObject(reader);
                        usersList.Add(temp);

                    }
                }

                return usersList;
            }
        }

        public User GetUser(string connectionString, int id)
        {
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                User result = new User();
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Can not establish a connection with the database.");
                }

                string sqlStatement = "SELECT * FROM Users WHERE Id = " + id;
                SqlCommand command = new SqlCommand(sqlStatement, conn);
                command.CommandType = System.Data.CommandType.Text;
                using (SqlDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        result = ConvertReaderToUserObject(reader);
                    }
                }

                return result;
            }
        }

        public User UpdateUser(string connectionString, User user)
        {
            if (string.IsNullOrEmpty(user.DisplayName) || user.DisplayName == " ")
                throw new Exception("The DisplayName should not be null or empty.");

            User result = new User();
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

                SqlCommand command = new SqlCommand("sp_UpdateUser", conn);
                command.CommandType = System.Data.CommandType.StoredProcedure;
                command.Parameters.Add(new SqlParameter("@Id", user.UserId));
                command.Parameters.Add(new SqlParameter("@AboutMe", user.AboutMe));
                command.Parameters.Add(new SqlParameter("@Age", user.Age));
                command.Parameters.Add(new SqlParameter("@DisplayName", user.DisplayName));
                command.Parameters.Add(new SqlParameter("@Location", user.Location));

                try
                {
                    using (SqlDataReader reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                            result = ConvertReaderToUserObject(reader);
                    }
                    return result;
                }
                catch (Exception ex)
                {
                    throw new Exception(ex.Message);
                }
            }
        }


        public void UpdateUserUpVote(string connectionString, int userId)
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

                string sqlStatement = "UPDATE Users SET UpVotes = ISNULL(UpVotes, 0) + 1 WHERE Id = " + userId;
                SqlCommand command = new SqlCommand(sqlStatement, conn);
                command.CommandType = System.Data.CommandType.Text;
                try
                {
                    command.ExecuteNonQuery();
                }catch
                {
                    throw new Exception("Can not update the user Upvotes field.");
                }

            }
        }

        public void UpdateUserDownVote(string connectionString, int userId)
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

                string sqlStatement = "UPDATE Users SET DownVotes = ISNULL(DownVotes, 0) + 1 WHERE Id = " + userId;
                SqlCommand command = new SqlCommand(sqlStatement, conn);
                command.CommandType = System.Data.CommandType.Text;
                try
                {
                    command.ExecuteNonQuery();
                }
                catch
                {
                    throw new Exception("Can not update the user DownVotes field.");
                }

            }
        }

        public int GetPostsTotalScoreForAUser(string connectionString, int userId)
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
                string sqlStatement = "SELECT SUM(Score) FROM Posts Where OwnerUserId=" + userId;
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
                    throw new Exception("Can not get the posts total score for userId" + userId);
                }

            }
        }

        public int UpdateUserReputation(string connectionString, int upVotes, int downVotes, int totalPostsScore, int userId)
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
                int totalScore = upVotes * 10 - downVotes * 10 + totalPostsScore;

                string sqlStatement = "UPDATE Users SET Reputation = " + totalScore + " WHERE Id = " + userId;
                SqlCommand command = new SqlCommand(sqlStatement, conn);
                command.CommandType = System.Data.CommandType.Text;
                try
                {
                    command.ExecuteNonQuery();
                    return totalScore;
                }
                catch
                {
                    throw new Exception("Can not update the user DownVotes field.");
                }
            }
            
        }
        #endregion

        #region Utilities
        public User ConvertReaderToUserObject(SqlDataReader reader)
        {
            User temp = new User();
            
            if (!reader.IsDBNull(reader.GetOrdinal("Id")))
                temp.UserId = reader.GetInt32(reader.GetOrdinal("Id"));

            if (!reader.IsDBNull(reader.GetOrdinal("AboutMe")))
                temp.AboutMe = reader.GetString(reader.GetOrdinal("AboutMe"));

            if (!reader.IsDBNull(reader.GetOrdinal("Age")))
                temp.Age = reader.GetInt32(reader.GetOrdinal("Age"));

            if (!reader.IsDBNull(reader.GetOrdinal("CreationDate")))
                temp.CreationDate = reader.GetDateTime(reader.GetOrdinal("CreationDate"));

            if (!reader.IsDBNull(reader.GetOrdinal("DisplayName")))
                temp.DisplayName = reader.GetString(reader.GetOrdinal("DisplayName"));

            if (!reader.IsDBNull(reader.GetOrdinal("DownVotes")))
                temp.DownVotes = reader.GetInt32(reader.GetOrdinal("DownVotes"));

            if (!reader.IsDBNull(reader.GetOrdinal("LastAccessDate")))
                temp.LastAccessDate = reader.GetDateTime(reader.GetOrdinal("LastAccessDate"));

            if (!reader.IsDBNull(reader.GetOrdinal("Location")))
                temp.Location = reader.GetString(reader.GetOrdinal("Location"));

            if (!reader.IsDBNull(reader.GetOrdinal("Reputation")))
                temp.Reputation = reader.GetInt32(reader.GetOrdinal("Reputation"));

            if (!reader.IsDBNull(reader.GetOrdinal("UpVotes")))
                temp.UpVotes = reader.GetInt32(reader.GetOrdinal("UpVotes"));

            if (!reader.IsDBNull(reader.GetOrdinal("Views")))
                temp.ViewsNumber = reader.GetInt32(reader.GetOrdinal("Views"));

            return temp;
        }
        #endregion
    }
}
