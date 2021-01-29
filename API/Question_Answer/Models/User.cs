using Question_Answer.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Web;
using Question_Answer_DataLayer;

namespace Question_Answer.Models
{
    public class User : IUser
    {
        #region Variables
        private Question_Answer_DataLayer.User userDataLayerObject;
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
        private string token;
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
        public string Token { get => token; set => token = value; }
        public string Password { get => password; set => password = value; }
        public int Role { get => role; set => role = value; }


        #endregion

        #region Contructor
        public User()
        {
            userDataLayerObject = new Question_Answer_DataLayer.User();
        }
        #endregion

        #region Methods
        public User Login(string connnectionString, string username, string password)
        {
            try
            {
                Question_Answer_DataLayer.User user = userDataLayerObject.Login(connnectionString, username, password);
                return (User)user;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }

        }

        public User Register(string connnectionString, User user)
        {
            try
            {
                Question_Answer_DataLayer.User userInfo = (Question_Answer_DataLayer.User)user;
                Question_Answer_DataLayer.User userResult = userDataLayerObject.Register(connnectionString, userInfo);
                return (User)userResult;
            } catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }

        }

        public List<User> GetUsers(string connectionString, int maxNumber = 0)
        {
            try
            {
                List<Question_Answer_DataLayer.User> list = userDataLayerObject.GetUsers(connectionString, maxNumber);
                List<User> usersList = new List<User>();

                foreach (Question_Answer_DataLayer.User user in list)
                {
                    User currentUser = (User)user;
                    usersList.Add(currentUser);
                }

                return usersList;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public User GetUser(string connectionString, int id)
        {
            try
            {
                Question_Answer_DataLayer.User dataUser = userDataLayerObject.GetUser(connectionString, id);
                User user = (User)dataUser;

                return user;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public User UpdateUser(string connectionString, User user)
        {
            try
            {
                Question_Answer_DataLayer.User userToDataLayer = (Question_Answer_DataLayer.User)user;
                Question_Answer_DataLayer.User resultDataLayer = userDataLayerObject.UpdateUser(connectionString, userToDataLayer);
                User result = (User)resultDataLayer;
                return result;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public void UpdateUserUpVotes(string connectionString, int userId)
        {
            try
            {
                userDataLayerObject.UpdateUserUpVote(connectionString, userId);
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public void UpdateUserDownVotes(string connectionString, int userId)
        {
            try
            {
                userDataLayerObject.UpdateUserDownVote(connectionString, userId);
            } catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public int GetPostsTotalScoreForAUser(string connectionString, int userId)
        {
            try
            {
                return userDataLayerObject.GetPostsTotalScoreForAUser(connectionString, userId);
            } catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public int UpdateUserReputation(string connectionString, int upVotes, int downVotes, int totalPostsScore, int userId)
        {
            try
            {
                userDataLayerObject.UpdateUserReputation(connectionString, upVotes, downVotes, totalPostsScore, userId);
            }catch(Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        #endregion

        #region Utilities
        public static explicit operator User(Question_Answer_DataLayer.User v)
        {
            User u = new User();
            u.AboutMe = v.AboutMe;
            u.Age = v.Age;
            u.CreationDate = v.CreationDate;
            u.DisplayName = v.DisplayName;
            u.DownVotes = v.DownVotes;
            u.Email = v.Email;
            u.LastAccessDate = v.LastAccessDate;
            u.Location = v.Location;
            u.Reputation = v.Reputation;
            u.UpVotes = v.UpVotes;
            u.UserId = v.UserId;
            u.Username = v.Username;
            u.ViewsNumber = v.ViewsNumber;
            u.Role = v.Role;
            return u;
        }

        public static explicit operator Question_Answer_DataLayer.User(User v)
        {
            Question_Answer_DataLayer.User u = new Question_Answer_DataLayer.User();
            u.AboutMe = v.AboutMe;
            u.Age = v.Age;
            u.CreationDate = v.CreationDate;
            u.DisplayName = v.DisplayName;
            u.DownVotes = v.DownVotes;
            u.Email = v.Email;
            u.LastAccessDate = v.LastAccessDate;
            u.Location = v.Location;
            u.Reputation = v.Reputation;
            u.UpVotes = v.UpVotes;
            u.UserId = v.UserId;
            u.Username = v.Username;
            u.Password = v.Password;
            u.ViewsNumber = v.ViewsNumber;
            u.Role = v.Role;
            return u;
        }
        #endregion
    }
}