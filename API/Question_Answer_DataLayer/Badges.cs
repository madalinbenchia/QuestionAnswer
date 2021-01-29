using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Text;

namespace Question_Answer_DataLayer
{
    public class Badges
    {
        #region Variables
        private int id;
        private string name;
        private int userId;
        private DateTime date;
        #endregion

        #region Properties
        public int Id { get => id; set => id = value; }
        public string Name { get => name; set => name = value; }
        public int UserId { get => userId; set => userId = value; }
        public DateTime Date { get => date; set => date = value; }
        #endregion

        #region Constructor
        public Badges() { }

        public Badges(string name, int userId, DateTime date)
        {
            this.Name = name;
            this.UserId = userId;
            this.Date = date;
        }
        
        public Badges(int id, string name, int userId, DateTime date)
        {
            this.Id = id;
            this.Name = name;
            this.UserId = userId;
            this.Date = date;
        }

        #endregion

        #region Methods
        public Badges AddBadge(string connectionString, Badges badge)
        {
            using (SqlConnection conn = new SqlConnection(connectionString))
            {
                try
                {
                    conn.Open();
                }
                catch
                {
                    throw new Exception("Unable to establish a connection with the database");
                }
                Badges badgeCreated = new Badges(); ;
                SqlCommand command = new SqlCommand("sp_AddBadge", conn);
                command.CommandType = System.Data.CommandType.StoredProcedure;
                command.Parameters.Add(new SqlParameter("@Name", badge.Name));
                command.Parameters.Add(new SqlParameter("@UserId", badge.UserId));
                command.Parameters.Add(new SqlParameter("@Date", badge.Date));
                try
                {
                    using(SqlDataReader reader = command.ExecuteReader())
                    {
                        while(reader.Read())
                        {
                            badgeCreated.Id = reader.GetInt32(0);
                            badgeCreated.Name = reader.GetString(1);
                            badgeCreated.UserId = reader.GetInt32(2);
                            badgeCreated.Date = reader.GetDateTime(3);
                        }
                    }
                    return badgeCreated;
                }
                catch (Exception ex)
                {
                   throw new Exception(ex.Message);
                }

            }
        }
        #endregion
    }
}
