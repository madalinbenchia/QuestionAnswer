using Question_Answer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Question_Answer.Interfaces
{
    interface IPost
    {
        List<Post> GetPosts(string connectionString, string tags = null);
    }
}
