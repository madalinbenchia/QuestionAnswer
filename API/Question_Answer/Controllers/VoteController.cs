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
    public class VoteController : ApiController
    {
        private Vote voteObject;
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        public VoteController()
        {
            voteObject = new Vote();
        }

        /// <summary>
        /// VoteTypeId - 2(UpVote), 3(DownVote), 5(Favorite)
        /// </summary>
        /// <param name="vote"></param>
        /// <returns></returns>
        [Route("api/vote/add")]
        [HttpPost]
        public HttpResponseMessage AddVote([FromBody]Vote vote)
        {
            try
            {
                string result = string.Empty;
                result = voteObject.AddVote(ConfigurationManager.AppSettings["connnectionString"],vote);
                if (result != string.Empty)
                    return Request.CreateResponse(System.Net.HttpStatusCode.OK, result);
                else
                    return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, "Vote was not added.");
            }
            catch(Exception ex)
            {
                log.Error(String.Format("Error adding vote. Error Message: {0} --- StackTrace: {1}", ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            } 
        }
    }
}
