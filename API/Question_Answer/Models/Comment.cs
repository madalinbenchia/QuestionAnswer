using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Question_Answer.Models
{
    public class Comment
    {
        #region Variables
        Question_Answer_DataLayer.Comment commentDataLayerObject;
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
            commentDataLayerObject = new Question_Answer_DataLayer.Comment();
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
            try
            {
                List<Question_Answer_DataLayer.Comment> dataLayerResult = commentDataLayerObject.GetComments(connectionString, postId);
                List<Comment> result = new List<Comment>();
                foreach(var temp in dataLayerResult)
                {
                    Comment currentCommment = (Comment)temp;
                    result.Add(currentCommment);
                }
                return result;
            }catch(Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        #endregion

        #region Utilities

        public static explicit operator Comment(Question_Answer_DataLayer.Comment v)
        {
            Comment result = new Comment();
            result.Id = v.Id;
            result.PostId = v.PostId;
            result.CreationDate = v.CreationDate;
            result.Score = v.Score;
            result.Text = v.Text;
            result.UserId = v.UserId;
            return result;
        }
        #endregion
    }
}