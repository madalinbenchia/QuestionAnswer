using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using Question_Answer.Models;
//using System.Web.Mvc;

namespace Question_Answer.Controllers
{
    public class PostController : ApiController
    {
        private Question questionObject;
        private Answer answerObject;
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        public PostController()
        {
            questionObject = new Question();
            answerObject = new Answer();
        }

        [Route("api/posts")]
        [HttpGet]
        public HttpResponseMessage GetPosts(int maxNumber = 0, string tags = null)
        {
            try
            {
                var result = questionObject.GetPosts(ConfigurationManager.AppSettings["connnectionString"], maxNumber, tags);
                return Request.CreateResponse(System.Net.HttpStatusCode.OK, result);
            }
            catch(Exception ex)
            {
                log.Error(String.Format("Error during get posts endpoint. Error Message: {0} --- StackTrace: {1}", ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            }
            
            
        }

        [Route("api/answers")]
        [HttpGet]
        public HttpResponseMessage GetAnswers(int parentId)
        {
            try
            {
                List<Post> result = answerObject.GetPosts(ConfigurationManager.AppSettings["connnectionString"], 0, null, parentId);
                return Request.CreateResponse(System.Net.HttpStatusCode.OK, result);
            }
            catch(Exception ex)
            {
                log.Error(String.Format("Error during get answers endpoint. Error Message: {0} --- StackTrace: {1}", ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }

        [Route("api/post/addquestion")]
        [HttpPost]
        public HttpResponseMessage AddQuestion([FromBody]Question question)
        {
            try
            {
                Question result = new Question();
                result = (Question)questionObject.AddPost(ConfigurationManager.AppSettings["connnectionString"], question);
                return Request.CreateResponse(System.Net.HttpStatusCode.OK, result);
            }
            catch(Exception ex)
            {
                log.Error(String.Format("Issue inserting a question. Error Message: {0} --- StackTrace: {1}", ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }

        [Route("api/post/updatequestion")]
        [HttpPut]
        public HttpResponseMessage UpdateQuestion([FromBody] Question question)
        {
            try
            {
                Question result = (Question)questionObject.UpdatePost(ConfigurationManager.AppSettings["connnectionString"], question);
                return Request.CreateResponse(System.Net.HttpStatusCode.OK, result);
            }
            catch(Exception ex)
            {
                log.Error(String.Format("Issue updating the question {0}. Error Message: {1} ---- StackTrace:{2}", question.Id, ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }


        [Route("api/post/deletequestion")]
        [HttpDelete]
        public HttpResponseMessage DeleteQuestion(int id)
        {
            try
            {
                string result = questionObject.DeletePost(ConfigurationManager.AppSettings["connnectionString"], id);
                return Request.CreateResponse(System.Net.HttpStatusCode.OK, result);
            }
            catch(Exception ex)
            {
                log.Error(String.Format("Issue deleting a question. Error Message: {0} --- StackTrace: {1}", ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }

        [Route("api/post/addanswer")]
        [HttpPost]
        public HttpResponseMessage AddAnswer([FromBody] Answer answer)
        {
            try
            {
                Answer result = new Answer();
                result = (Answer)answerObject.AddPost(ConfigurationManager.AppSettings["connnectionString"], answer);
                return Request.CreateResponse(System.Net.HttpStatusCode.OK, result);
            }
            catch (Exception ex)
            {
                log.Error(String.Format("Issue inserting an answer. Error Message: {0} --- StackTrace: {1}", ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }

        [Route("api/post/deleteanswer")]
        [HttpDelete]
        public HttpResponseMessage DeleteAnswer(int id)
        {
            try
            {
                string result = answerObject.DeletePost(ConfigurationManager.AppSettings["connnectionString"], id);
                return Request.CreateResponse(System.Net.HttpStatusCode.OK, result);
            }
            catch (Exception ex)
            {
                log.Error(String.Format("Issue deleting an answer. Error Message: {0} --- StackTrace: {1}", ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }

        [Route("api/post/updateanswer")]
        [HttpPut]
        public HttpResponseMessage UpdateAnswer([FromBody] Answer answer)
        {
            try
            {
                Answer result = (Answer)answerObject.UpdatePost(ConfigurationManager.AppSettings["connnectionString"], answer);
                return Request.CreateResponse(System.Net.HttpStatusCode.OK, result);
            }
            catch (Exception ex)
            {
                log.Error(String.Format("Issue updating the question {0}. Error Message: {1} ---- StackTrace:{2}", answer.Id, ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }

    }
}