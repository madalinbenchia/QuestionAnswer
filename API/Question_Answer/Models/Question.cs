using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Question_Answer.Models
{
    public class Question : Post
    {
        private Question_Answer_DataLayer.Post postDataLayerObject;
        public Question()
        {
            postDataLayerObject = new Question_Answer_DataLayer.Post();
        }

        #region Methods
        public override List<Post> GetPosts(string connnectionString, int maxNumber = 0, string tags = null, int parentId = 0)
        {
            try
            {

                List<Question_Answer_DataLayer.Post> list = postDataLayerObject.GetPosts(connnectionString, maxNumber, tags);
                List<Post> postsList = new List<Post>();

                foreach (Question_Answer_DataLayer.Post post in list)
                {
                    Question currentPost = (Question)post;
                    postsList.Add(currentPost);
                }

                return postsList;
            }

            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public override Post AddPost(string connectionString, Post post)
        {
            try
            {
                Question_Answer_DataLayer.Post questionToDataLayer = (Question_Answer_DataLayer.Post)post;
                questionToDataLayer.CreationDate = DateTime.Now;
                questionToDataLayer.LastActivityDate = DateTime.Now;
                Question_Answer_DataLayer.Post resultDataLayer = postDataLayerObject.AddQuestion(connectionString, questionToDataLayer);
                Question result = (Question)resultDataLayer;
                return result;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public override string DeletePost(string connectionString, int id)
        {
            try
            {
                string result = postDataLayerObject.DeleteQuestion(connectionString, id);
                return result;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public override Post UpdatePost(string connectionString, Post post)
        {
            try
            {
                Question_Answer_DataLayer.Post questionToDataLayer = (Question_Answer_DataLayer.Post)post;
                Question_Answer_DataLayer.Post resultDataLayer = postDataLayerObject.UpdateQuestion(connectionString, questionToDataLayer);
                Question result = (Question)resultDataLayer;
                return result;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        public List<Post> SearchPosts(string connnectionString, string searchString = null)
        {
            try
            {

                List<Question_Answer_DataLayer.Post> list = postDataLayerObject.SearchPosts(connnectionString, searchString);
                List<Post> postsList = new List<Post>();

                foreach (Question_Answer_DataLayer.Post post in list)
                {
                    Question currentPost = (Question)post;
                    postsList.Add(currentPost);
                }

                return postsList;
            }

            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }
        #endregion

        #region Utilities
        public static explicit operator Question(Question_Answer_DataLayer.Post q)
        {
            Question p = new Question();
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