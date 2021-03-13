CREATE TABLE `acounts` (
                           `AccountId` int(11) NOT NULL AUTO_INCREMENT,
                           `Username` varchar(50) NOT NULL,
                           `Password` varchar(50) NOT NULL,
                           `Role` int(11) DEFAULT NULL,
                           PRIMARY KEY (`AccountId`)
);

CREATE TABLE `comments` (
                            `CommentId` int(11) NOT NULL AUTO_INCREMENT,
                            `CreationDate` datetime NOT NULL,
                            `PostId` int(11) NOT NULL,
                            `Score` int(11) DEFAULT NULL,
                            `Text` varchar(1000)  NOT NULL,
                            `UserId` int(11) NOT NULL,
                            PRIMARY KEY (`CommentId`)
);

CREATE TABLE `posts` (
                         `Id` int(11) NOT NULL AUTO_INCREMENT,
                         `AcceptedAnswerId` int(11) DEFAULT NULL,
                         `AnswerCount` int(11) DEFAULT NULL,
                         `Body` varchar(1000)  DEFAULT NULL,
                         `ClosedDate` datetime DEFAULT NULL,
                         `CommentCount` int(11) DEFAULT NULL,
                         `CreationDate` datetime DEFAULT NULL,
                         `FavoriteCount` int(11) DEFAULT NULL,
                         `LastActivityDate` datetime DEFAULT NULL,
                         `LastEditDate` datetime DEFAULT NULL,
                         `LastEditorDisplayName` varchar(1000)  DEFAULT NULL,
                         `LastEditorUserId` int(11) DEFAULT NULL,
                         `OwnerUserId` int(11) DEFAULT NULL,
                         `ParentId` int(11) DEFAULT NULL,
                         `PostTypeId` int(11) DEFAULT NULL,
                         `Score` int(11) DEFAULT NULL,
                         `Tags` varchar(1000)  DEFAULT NULL,
                         `Title` varchar(100)  DEFAULT NULL,
                         `ViewCount` int(11) DEFAULT NULL,
                         PRIMARY KEY (`Id`)
) ;

CREATE TABLE `posttypes` (
                             `Id` int(11) NOT NULL AUTO_INCREMENT,
                             `Type` varchar(50)  DEFAULT NULL,
                             PRIMARY KEY (`Id`)
);

CREATE TABLE `users` (
                         `Id` int(11) NOT NULL AUTO_INCREMENT,
                         `AboutMe` varchar(5000)  DEFAULT NULL,
                         `Age` int(11) DEFAULT NULL,
                         `CreationDate` datetime DEFAULT NULL,
                         `DisplayName` varchar(45) DEFAULT NULL,
                         `DownVotes` int(11) DEFAULT NULL,
                         `Location` varchar(100)  DEFAULT NULL,
                         `Reputation` int(11) DEFAULT NULL,
                         `UpVotes` int(11) DEFAULT NULL,
                         `Views` int(11) DEFAULT NULL,
                         `AccountId` int(11) DEFAULT NULL,
                         PRIMARY KEY (`Id`)
);

CREATE TABLE `votes` (
                         `Id` int(11) NOT NULL AUTO_INCREMENT,
                         `PostId` int(11) DEFAULT NULL,
                         `UserId` int(11) DEFAULT NULL,
                         `VoteTypeId` int(11) DEFAULT NULL,
                         `CreationDate` datetime DEFAULT NULL,
                         PRIMARY KEY (`Id`)
);


--insert account
INSERT INTO 'acounts'('AccountId','Username','Password') VALUES (1,'Test','Test');

--Insert user
INSERT INTO 'users'('Id','AboutMe', 'Age','CreationDate','DisplayName','DownVotes','Location','Reputation','UpVotes','Views','AccountId')
VALUES(1,'Test about me',30,NOW(),'Test Display Name',0,'Romania',0,0,0,1);

--Insert question
INSERT INTO 'posts'('Id','AcceptedAnswerId','AnswerCount','Body','ClosedDate','CommentCount','CreationDate','FavoriteCount','LastActivityDate','LastEditDate','LastEditorDisplayName','LastEditorUserId','OwnerUserId','ParentId','PostTypeId','Score','Tags','Title','ViewCount')
VALUES (1,0,1,'Test Question body',NULL,0,NOW(),0,NOW(),NULL,NULL,NULL,1,0,1,0,'Test tag','Test Question Title',0);

--Insert answer
INSERT INTO 'posts'('Id','AcceptedAnswerId','AnswerCount','Body','ClosedDate','CommentCount','CreationDate','FavoriteCount','LastActivityDate','LastEditDate','LastEditorDisplayName','LastEditorUserId','OwnerUserId','ParentId','PostTypeId','Score','Tags','Title','ViewCount')
VALUES (2,0,1,'Test Answer body',NULL,1,NOW(),0,NOW(),NULL,NULL,NULL,1,1,1,0,'Test tag','Test Answer Title',0);

--Insert comment
INSERT INTO 'comments'('CommentId','CreationDate','PostId','Text','UserId') VALUES (1,NOW(),2,'Text Comment',1)


