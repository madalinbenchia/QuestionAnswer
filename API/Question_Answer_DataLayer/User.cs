using System;
using System.Collections.Generic;
using System.Text;

namespace Question_Answer_DataLayer
{
    class User 
    {
        #region Variables
        private int id;
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
        #endregion

        #region Properties
        public int Id { get => id; set => id = value; }
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
        #endregion

        #region Methods

        #endregion
    }
}
