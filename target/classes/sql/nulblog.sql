/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.14 : Database - nulblog
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`nulblog` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `nulblog`;

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL COMMENT '文章标题',
  `content` text NOT NULL,
  `tags` varchar(64) DEFAULT NULL COMMENT '标签 可以多个 最多5个',
  `categoryid` varchar(11) NOT NULL DEFAULT '""' COMMENT '分类 只能1个  为空就是默认分类',
  `authorid` varchar(32) NOT NULL COMMENT '作者',
  `status` varchar(1) NOT NULL DEFAULT '0' COMMENT '状态默认保存为草稿0	保存发布1',
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `markdowncontent` text,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `article` */

insert  into `article`(`aid`,`title`,`content`,`tags`,`categoryid`,`authorid`,`status`,`created`,`modified`,`markdowncontent`) values (1,'标题1','<h3 id=\"h3-u6807u9898\"><a name=\"标题\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>标题</h3><p>内容</p>\n','标签1,标签2','1','2b0bb9b78a9444189eb267778b8c06c5','1','2018-06-04 16:24:49','2018-06-04 16:24:49','###标题\n内容');

/*Table structure for table `b_user` */

DROP TABLE IF EXISTS `b_user`;

CREATE TABLE `b_user` (
  `buid` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(64) NOT NULL,
  `email` varchar(64) NOT NULL,
  `created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`buid`,`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `b_user` */

insert  into `b_user`(`buid`,`username`,`password`,`email`,`created`) values ('2b0bb9b78a9444189eb267778b8c06c5','admin','59f2443a4317918ce29ad28a14e1bdb7','tuyi954456157@qq.com','2018-04-12 18:58:37');

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL COMMENT '标签分类名称 10个中文',
  `authorId` varchar(32) NOT NULL COMMENT '作者id',
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Data for the table `category` */

insert  into `category`(`cid`,`name`,`authorId`) values (1,'分类1','2b0bb9b78a9444189eb267778b8c06c5');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
