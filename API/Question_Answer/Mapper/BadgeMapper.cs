using Question_Answer.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Question_Answer.Mapper
{
    public class BadgeMapper
    {
        public Question_Answer_DataLayer.Badges BadgeToBadgesDataLayer(Badge badge)
        {
            return new Question_Answer_DataLayer.Badges(badge.Id, badge.Name, badge.UserId, badge.Date);
        }

        public Badge BadgeDataLayerToBadge(Question_Answer_DataLayer.Badges badge)
        {
            return new Badge(badge.Id, badge.Name, badge.UserId, badge.Date);
        }
    }
}