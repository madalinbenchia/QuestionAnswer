using Question_Answer_DataLayer;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Web;

namespace Question_Answer_Presentation_Layer.Models
{
    public class QuestionUIPage
    {
        public class Commment
        {
            Question_Answer_DataLayer.Comment commentDataLayerObject;

            #region Variables
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
            public Commment()
            {
                commentDataLayerObject = new Question_Answer_DataLayer.Comment();
            }
            #endregion

            #region Utilities
            public static explicit operator Commment(Comment v)
            {
                Commment comm = new Commment();
                comm.Id = v.Id;
                comm.CreationDate = v.CreationDate;
                comm.PostId = v.PostId;
                comm.Score = v.Score;
                comm.Text = v.Text;
                comm.UserId = v.UserId;
                return comm;
            }
            #endregion


            #region Methods
            public List<Commment> GetAllCommentsForAnAnswer(string connectionString, int answerId)
            {
                try
                {
                    List<Question_Answer_DataLayer.Comment> commentsList = commentDataLayerObject.GetComments(connectionString, answerId);
                    List<Commment> commentsResult = new List<Commment>();
                    foreach (var temp in commentsList)
                    {
                        Commment currrentComment = (Commment)temp;
                        commentsResult.Add(currrentComment);
                    }
                    return commentsResult;
                }
                catch (Exception ex)
                {
                    throw new Exception(ex.Message);
                }
            }
            #endregion


        }
       public class Answer
        {
            Question_Answer_DataLayer.Post postDataLayerObject;

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
            private List<Commment> commentsList;
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
            public
                List<Commment> CommentsList { get => commentsList; set => commentsList = value; }
            #endregion

            #region Constructor
            public Answer()
            {
                postDataLayerObject = new Question_Answer_DataLayer.Post();

            }
            #endregion

            #region Methods
            public List<Answer> GetAnswersForAQuestion(string connectionString, int questionId)
            {
                try
                {
                    List<Question_Answer_DataLayer.Post> postList = postDataLayerObject.GetAnswers(connectionString, questionId);
                    List<Answer> result = new List<Answer>();
                    foreach (var post in postList)
                    {
                        Answer currentAnswer = (Answer)post;
                        result.Add(currentAnswer);
                    }
                    return result;
                }
                catch (Exception ex)
                {
                    throw new Exception(ex.Message);
                }
            }

            #endregion

            #region Utilities
            public static explicit operator Answer(Post q)
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
        public class Question
        {
            Question_Answer_DataLayer.Post postDataLayerObject;
            Answer answer;
            Commment comment;


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
            private List<Answer> answersList;
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
            public List<Answer> AnswersList { get => answersList; set => answersList = value; }
            #endregion

            #region Constructor
            public Question()
            {
                postDataLayerObject = new Question_Answer_DataLayer.Post();
                answer = new Answer();
                comment = new Commment();
                
                this.AnswersList = new List<Answer>();

            }
            #endregion
            #region Methods
            public Question GetQuestion(string connectionString, int id)
            
            {
                try
                {

                    Question_Answer_DataLayer.Post post = postDataLayerObject.GetPost(connectionString, id);
                    Question question = (Question)post;
                    //fill the question object with the answers
                    var answers = answer.GetAnswersForAQuestion(connectionString, question.Id);
                    foreach (var tempAnswer in answers)
                        question.AnswersList.Add(tempAnswer);


                    //foreach answer, we need to fill with comments
                    foreach (var temp in question.AnswersList)
                    {
                        var comments = comment.GetAllCommentsForAnAnswer(connectionString, temp.Id);
                        temp.CommentsList = new List<Commment>();
                        foreach (var comm in comments)
                            temp.CommentsList.Add(comm);
                    }

                    return question;
                }

                catch (Exception ex)
                {
                    throw new Exception(ex.Message);
                }
            }


            #endregion

            #region Utilities
            public static explicit operator Question(Post q)
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

    

}