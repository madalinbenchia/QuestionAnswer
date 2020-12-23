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
                string sqlCommand = "Select ac.Username, ac.Password, ac.Role, u.Id as UserId, u.AboutMe, u.Age, u.CreationDate, u.DisplayName, u.DownVotes, u.EmailHash, u.LastAccessDate, u.Location, u.Reputation, u.UpVotes, u.Views, u.WebsiteUrl, u.AccountId" +
                    "from dbo.Accounts ac inner join dbo.Users u on ac.AccountId = u.AccountId" + 
                    "where ac.Username = " + username + " and ac.Password = " + password;
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

        }
        #endregion
    }
}
