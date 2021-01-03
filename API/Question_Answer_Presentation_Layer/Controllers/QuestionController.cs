using Question_Answer_Presentation_Layer.Models;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Question_Answer_Presentation_Layer.Controllers
{
    public class QuestionController : ApiController
    {
        private QuestionUIPage.Question questionObject;

        #region Constructor
        public QuestionController()
        {
            questionObject = new QuestionUIPage.Question();
        }

        #endregion

        #region Methods

        [Route("api/question")]
        [HttpGet]
        public HttpResponseMessage GetQuestionInfo(int id)
        {
            try
            {
                var result = questionObject.GetQuestion(ConfigurationManager.AppSettings["connnectionString"], id);
                return Request.CreateResponse(HttpStatusCode.OK, result);
            }
            catch(Exception ex)
            {
                return Request.CreateResponse(HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }
        #endregion
    }
}
