using Question_Answer_Presentation_Layer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Question_Answer_Presentation_Layer.Mapper
{
    public class UserMapper
    {
        public User UserDataLayerToUser(Question_Answer_DataLayer.User user)
        {
            return new User(user.UserId, user.AboutMe, user.Age, user.CreationDate, user.LastAccessDate, user.DisplayName, user.UpVotes, user.DownVotes, user.Email, user.Reputation, user.ViewsNumber, user.Username, user.Location, user.Password, user.Role);
        }
    }
}