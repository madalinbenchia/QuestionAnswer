using Question_Answer.Interfaces;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Question_Answer.Models
{
    public abstract class Post
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
        public abstract List<Post> GetPosts(string connnectionString, int maxNumber = 0, string tags = null, int parentId = 0);

        public abstract Post AddPost(string connectionString, Post post);

        public abstract string DeletePost(string connectionString, int id);
        public abstract Post UpdatePost(string connectionString, Post question);



        #endregion

        #region Utilities
        /*public static explicit operator Post(Question_Answer_DataLayer.Post q)
        {
            Post p = new Post();
            p.Id = q.Id;
            p.AcceptedAnswerId = q.AcceptedAnswerId;
            p.AnswerCount = q.AnswerCount;
            p.Body = q.Body;
            p.CloseDate = q.CloseDate;
            p.CommentCount = q.CommentCount;
            p.CreationDate = q.CreationDate;
            p.FavouriteCount = q.FavouriteCount;
            p.LastActivityDate = q.LastActivityDate;
            p.LastEditDate = q.LastEditDate;
            p.LastEditorDisplayName = q.LastEditorDisplayName;
            p.LastEditorUserId = q.LastEditorUserId;
            p.OwnerUserId = q.OwnerUserId;
            p.ParentId = q.ParentId;
            p.PostTypeId = q.PostTypeId;
            p.Score = q.Score;
            p.Tags = q.Tags;
            p.Title = q.Title;
            p.ViewCount = q.ViewCount;
            return p;
        }*/


        public static explicit operator Question_Answer_DataLayer.Post(Post q)
        {
            Question_Answer_DataLayer.Post p = new Question_Answer_DataLayer.Post();
            p.Id = q.Id;
            p.AcceptedAnswerId = q.AcceptedAnswerId;
            p.AnswerCount = q.AnswerCount;
            p.Body = q.Body;
            p.CloseDate = q.CloseDate;
            p.CommentCount = q.CommentCount;
            p.CreationDate = q.CreationDate;
            p.FavouriteCount = q.FavouriteCount;
            p.LastActivityDate = q.LastActivityDate;
            p.LastEditDate = q.LastEditDate;
            p.LastEditorDisplayName = q.LastEditorDisplayName;
            p.LastEditorUserId = q.LastEditorUserId;
            p.OwnerUserId = q.OwnerUserId;
            p.ParentId = q.ParentId;
            p.PostTypeId = q.PostTypeId;
            p.Score = q.Score;
            p.Tags = q.Tags;
            p.Title = q.Title;
            p.ViewCount = q.ViewCount;
            return p;
        }
        #endregion
    }
}