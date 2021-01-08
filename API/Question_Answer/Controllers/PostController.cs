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
        private Post postObject;
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        public PostController()
        {
            postObject = new Post();
        }

        [Route("api/posts")]
        [HttpGet]
        public HttpResponseMessage GetPosts(int maxNumber = 0, string tags = null)
        {
            try
            {
                var result = postObject.GetPosts(ConfigurationManager.AppSettings["connnectionString"], maxNumber, tags);
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
                List<Post> result = postObject.GetAnswers(ConfigurationManager.AppSettings["connnectionString"], parentId);
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
        public HttpResponseMessage AddQuestion([FromBody]Post question)
        {
            try
            {
                Post result = new Post();
                result = postObject.AddQuestion(ConfigurationManager.AppSettings["connnectionString"], question);
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
        public HttpResponseMessage UpdateQuestion([FromBody] Post question)
        {
            try
            {
                Post result = postObject.UpdateQuestion(ConfigurationManager.AppSettings["connnectionString"], question);
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
                string result = postObject.DeleteQuestion(ConfigurationManager.AppSettings["connnectionString"], id);
                return Request.CreateResponse(System.Net.HttpStatusCode.OK, result);
            }
            catch(Exception ex)
            {
                log.Error(String.Format("Issue deleting a question. Error Message: {0} --- StackTrace: {1}", ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }

    }
}