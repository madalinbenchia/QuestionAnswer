using System;
using System.Collections.Generic;
using System.Linq;
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

        [Route("api/post")]
        [HttpGet]
        public List<Post> GetPosts(string connnectionString, string tags = null)
        {
            return postObject.GetPosts(connnectionString, tags);
            //to do, idk why it doesn't work.
        }
    }
}