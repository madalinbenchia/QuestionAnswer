using Microsoft.IdentityModel.Tokens;
using Question_Answer.Models;
using System;
using System.Collections.Generic;
using System.Configuration;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Net.Http;
using System.Security.Claims;
using System.Text;
using System.Web;
using System.Web.Http;
//using System.Web.Mvc;
using HttpPostAttribute = System.Web.Http.HttpPostAttribute;
using RouteAttribute = System.Web.Http.RouteAttribute;

namespace Question_Answer.Controllers
{
    public class UserController : ApiController
    {
        private User userObject;
        private static readonly log4net.ILog log = log4net.LogManager.GetLogger(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
        public UserController()
        {
            userObject  = new User();
        }


        [Route("api/user/login")]
        [HttpPost]
        public HttpResponseMessage Login([FromBody] User user)
        {
            //password comes encoded in Base64
            //need tp decode before to send it to Model
            byte[] data = Convert.FromBase64String(user.Password);
            string decodedPassword = Encoding.UTF8.GetString(data);
            //we don't want to send back as response the password
            user.Password = string.Empty;
            try
            {
                User userResult = userObject.Login(ConfigurationManager.AppSettings["connnectionString"], user.Username, decodedPassword);
                userResult.Token = GenerateJSONWebToken(userResult);
                return Request.CreateResponse(System.Net.HttpStatusCode.OK, userResult);
            }
            catch(Exception ex)
            {
                log.Error(String.Format("Issue during login -- Error Message: {0} -- StackTrace: {1}", ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }

        [Route("api/user/register")]
        [HttpPost]
        public HttpResponseMessage Register([FromBody] User user)
        {
            //password comes encoded in Base64
            //need tp decode before to send it to Model
            byte[] data = Convert.FromBase64String(user.Password);
            string decodedPassword = Encoding.UTF8.GetString(data);
            try
            {
                User userResult = userObject.Register(ConfigurationManager.AppSettings["connnectionString"], user);
                try
                {
                    userResult.Token = GenerateJSONWebToken(userResult);
                    return Request.CreateResponse(System.Net.HttpStatusCode.OK, userResult);
                }
                catch(Exception ex)
                {
                    log.Error(String.Format("Issue during register. Error Message: {0} -- Error StackTrace: {1}", ex.Message, ex.StackTrace));
                    return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
                }
                
            }
            catch(Exception ex)
            {
                log.Error(String.Format("Issue during register -- Error Message: {0} -- StackTrace: {1}", ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            }

        }

        [Route("api/user")]
        [HttpGet]
        public HttpResponseMessage GetUsers(int maxNumber = 0)
        {
            try
            {
                var result = userObject.GetUsers(ConfigurationManager.AppSettings["connnectionString"], maxNumber);
                return Request.CreateResponse(System.Net.HttpStatusCode.OK, result);
            }
            catch (Exception ex)
            {
                log.Error(String.Format("Error during get users endpoint. Error Message: {0} --- StackTrace: {1}", ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }

        [Route("api/me")]
        [HttpGet]
        public HttpResponseMessage GetUser(int id = 0)
        {
            try
            {
                var result = userObject.GetUser(ConfigurationManager.AppSettings["connnectionString"], id);
                return Request.CreateResponse(System.Net.HttpStatusCode.OK, result);
            }
            catch (Exception ex)
            {
                log.Error(String.Format("Error during get user by id endpoint. Error Message: {0} --- StackTrace: {1}", ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }

        [Route("api/user/updateuser")]
        [HttpPut]
        public HttpResponseMessage UpdateUser([FromBody] User user)
        {
            try
            {
                User result = (User)userObject.UpdateUser(ConfigurationManager.AppSettings["connnectionString"], user);
                return Request.CreateResponse(System.Net.HttpStatusCode.OK, result);
            }
            catch (Exception ex)
            {
                log.Error(String.Format("Issue updating user {0}. Error Message: {1} ---- StackTrace:{2}", user.UserId, ex.Message, ex.StackTrace));
                return Request.CreateResponse(System.Net.HttpStatusCode.ExpectationFailed, ex.Message);
            }
        }

        #region Utilities
        private string GenerateJSONWebToken(User userInfo)
        {
            var securityKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(ConfigurationManager.AppSettings["TokenSecret"]));
            var credentials = new SigningCredentials(securityKey, SecurityAlgorithms.HmacSha256);

            var claims = new[] {
                new Claim(JwtRegisteredClaimNames.Sub, userInfo.Username),
                new Claim("Role",Convert.ToString(userInfo.Role)),
                new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString())
            };
            try
            {
                var token = new JwtSecurityToken("QAIssuer",
              "QAIssuer",
              claims,
              expires: DateTime.Now.AddMinutes(120),
              signingCredentials: credentials);

                return new JwtSecurityTokenHandler().WriteToken(token);
            }
            catch(Exception ex)
            {
                log.Error(String.Format("Issue generating JWT token. Error Message: {0} -- StackTrace: {1}", ex.Message, ex.StackTrace));
                throw new Exception(ex.Message);
            }
            
        }
        #endregion
    }
}