using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Question_Answer.Models
{
    public class Answer : Post
    {
        private Question_Answer_DataLayer.Post postDataLayerObject;

        public Answer()
        {
            postDataLayerObject = new Question_Answer_DataLayer.Post();
        }

        public override List<Post> GetPosts(string connnectionString, int maxNumber = 0, string tags = null, int parentId = 0)
        {
            try
            {
                List<Question_Answer_DataLayer.Post> list = postDataLayerObject.GetAnswers(connnectionString, parentId);
                List<Post> answerList = new List<Post>();
                foreach (Question_Answer_DataLayer.Post answer in list)
                {
                    Answer currentPost = (Answer)answer;
                    answerList.Add(currentPost);
                }

                return answerList;
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
                Question_Answer_DataLayer.Post answerToDataLayer = (Question_Answer_DataLayer.Post)post;
                answerToDataLayer.CreationDate = DateTime.Now;
                answerToDataLayer.LastActivityDate = DateTime.Now;
                Question_Answer_DataLayer.Post resultDataLayer = postDataLayerObject.AddAnswer(connectionString, answerToDataLayer);
                Answer result = (Answer)resultDataLayer;
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
                string result = postDataLayerObject.DeleteAnswer(connectionString, id);
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
                questionToDataLayer.LastActivityDate = DateTime.Now;
                questionToDataLayer.LastEditDate = DateTime.Now;
                Question_Answer_DataLayer.Post resultDataLayer = postDataLayerObject.UpdateAnswer(connectionString, questionToDataLayer);
                Answer result = (Answer)resultDataLayer;
                return result;
            }
            catch (Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }

        #region Utilities
        public static explicit operator Answer(Question_Answer_DataLayer.Post q)
        {
            Answer p = new Answer();
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