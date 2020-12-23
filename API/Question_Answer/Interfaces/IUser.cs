using Question_Answer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Question_Answer.Interfaces
{
    interface IUser
    {
        User Login(string connectionString, string username, string password);
        User Register(string connectionString, string username, string password);
    }
}
