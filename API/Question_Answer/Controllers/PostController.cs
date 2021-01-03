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

        //to do:
        // To add an extra parameter to retrieve top 'x' questions(posts with parentId = 0)
        // optiona parameter with 0 as default value
        [Route("api/posts")]
        [HttpGet]
        public HttpResponseMessage GetPosts(string tags = null)
        {
            try
            {
                var result = postObject.GetPosts(ConfigurationManager.AppSettings["connnectionString"], tags);
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

    }
}