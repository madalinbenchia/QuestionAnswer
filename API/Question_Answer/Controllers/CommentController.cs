using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using Question_Answer.Models;


namespace Question_Answer.Controllers
{
    public class CommentController : ApiController
    {
        private Comment commentObject;
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        public CommentController()
        {
            commentObject = new Comment();
        }

        #region Methods
        [Route("api/comments")]
        [HttpGet]
        public HttpResponseMessage GetComment(int postId)
        {
            try
            {
                var result = commentObject.GetComments(ConfigurationManager.AppSettings["connnectionString"], postId);
                return Request.CreateResponse(System.Net.HttpStatusCode.OK, result);
            }
            catch (Exception ex)
            {
                log.Error(String.Format("Error during get comments endpoint. Error Message: {0} --- StackTrace: {1}", ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }

        [Route("api/comment/addcomment")]
        [HttpPost]
        public HttpResponseMessage AddComment([FromBody] Comment comment)
        {
            try
            {
                Comment result = new Comment();
                result = commentObject.AddComment(ConfigurationManager.AppSettings["connnectionString"], comment);
                return Request.CreateResponse(System.Net.HttpStatusCode.OK, result);
            }
            catch (Exception ex)
            {
                log.Error(String.Format("Issue inserting a comment. Error Message: {0} --- StackTrace: {1}", ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }

        [Route("api/comment/deletecomment")]
        [HttpDelete]
        public HttpResponseMessage DeleteComment(int id)
        {
            try
            {
                string result = commentObject.DeleteComment(ConfigurationManager.AppSettings["connnectionString"], id);
                return Request.CreateResponse(System.Net.HttpStatusCode.OK, result);
            }
            catch (Exception ex)
            {
                log.Error(String.Format("Issue deleting a comment. Error Message: {0} --- StackTrace: {1}", ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }
        #endregion
    }
}
