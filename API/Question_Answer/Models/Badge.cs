using Question_Answer.Mapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Question_Answer.Models
{
    public class Badge
    {
        private Question_Answer_DataLayer.Badges badgeDataLayerObject;
        private BadgeMapper badgeMapper;
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

        public Badge()
        {
            badgeDataLayerObject = new Question_Answer_DataLayer.Badges();
            badgeMapper = new BadgeMapper();
        }

        public Badge(int id, string name, int userId, DateTime date)
        {
            this.Id = id;
            this.Name = name;
            this.UserId = userId;
            this.Date = date;
        }

        #region Methods
        public Badge AddBadge(string connectionString, Badge badge)
        {
            try
            {
                return badgeMapper.BadgeDataLayerToBadge(badgeDataLayerObject.AddBadge(connectionString, badgeMapper.BadgeToBadgesDataLayer(badge)));
            }catch(Exception ex)
            {
                throw new Exception(ex.Message);
            }
        }
        #endregion
    }
}