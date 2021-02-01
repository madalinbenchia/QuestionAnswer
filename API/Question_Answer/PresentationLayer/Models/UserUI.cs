using Question_Answer_DataLayer;
using Question_Answer_Presentation_Layer.Mapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Question_Answer_Presentation_Layer.Models
{
    public class UserUI
    {
        #region Variables
        private Question_Answer_DataLayer.User userDataLayerObject;
        private Question_Answer_DataLayer.Badges badgesDataLayerObject;
        private UserMapper userMapper;
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
        private List<Badges> badges;
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
        public List<Badges> Badges { get => badges; set => badges = value; }


        #endregion

        #region Constructor

        public UserUI()
        {
            userMapper = new UserMapper();
            userDataLayerObject = new Question_Answer_DataLayer.User();
            badgesDataLayerObject = new Badges();
        }
        public UserUI(int userId, string aboutMe, int age, DateTime creationDate, DateTime lastAccessDate, string displayName, int upVotes, int downVotes, string email, int reputation, int viewsNumber, string userName, string location, string password, int role)
        {
            this.UserId = userId;
            this.AboutMe = aboutMe;
            this.Age = age;
            this.creationDate = creationDate;
            this.LastAccessDate = lastAccessDate;
            this.DisplayName = displayName;
            this.UpVotes = upVotes;
            this.DownVotes = downVotes;
            this.Email = email;
            this.Reputation = reputation;
            this.ViewsNumber = viewsNumber;
            this.Username = userName;
            this.Location = location;
            this.Password = password;
            this.Role = role;
        }
        #endregion

        #region Methods
        public List<UserUI> GetAllUsersWithBadges(string connectionString, int maxNumber = 0)
        {
            List<UserUI> resullt = new List<UserUI>();
            try
            {
                List<Question_Answer_DataLayer.User> users = userDataLayerObject.GetUsers(connectionString, maxNumber);
                foreach (var user in users)
                {
                    UserUI tempUser = userMapper.UserDataLayerToUser(user);
                    tempUser.Badges = badgesDataLayerObject.GetAllBadgesForAnUser(connectionString, user.UserId);
                    resullt.Add(tempUser);
                }

                return resullt;
            }catch
            {
                return new List<UserUI>();
            }
            
        }

        public UserUI GetUserWithBadges(string connectionString, int userId)
        {
            try
            {
                UserUI result = new UserUI();
                Question_Answer_DataLayer.User userDataLayer = userDataLayerObject.GetUser(connectionString, userId);
                result = userMapper.UserDataLayerToUser(userDataLayer);
                result.badges = badgesDataLayerObject.GetAllBadgesForAnUser(connectionString, userId);
                return result;
            }catch(Exception ex)
            {
                throw new Exception(ex.Message);
            }
            
        }
        #endregion
    }
}