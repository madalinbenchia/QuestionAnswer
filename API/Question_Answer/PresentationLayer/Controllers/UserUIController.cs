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
    public class UserUIController : ApiController
    {
        private UserUI userObject;

        #region Constructor
        public UserUIController()
        {
            userObject = new UserUI();
        }
        #endregion

        #region Methods
        [Route("api/allusers")]
        [HttpGet]
        public HttpResponseMessage GetUsersWithBadges(int maxNumber = 0)
        {
            try
            {
                var result = userObject.GetAllUsersWithBadges(ConfigurationManager.AppSettings["connnectionString"], maxNumber);
                return Request.CreateResponse(HttpStatusCode.OK, result);
            }catch(Exception ex)
            {
                return Request.CreateResponse(HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }

        [Route("api/userdetails")]
        [HttpGet]
        public HttpResponseMessage GetUserWithBadges(int id)
        {
            try
            {
                var result = userObject.GetUserWithBadges(ConfigurationManager.AppSettings["connnectionString"], id);
                return Request.CreateResponse(HttpStatusCode.OK, result);
            }
            catch (Exception ex)
            {
                return Request.CreateResponse(HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }
        #endregion
    }
}
