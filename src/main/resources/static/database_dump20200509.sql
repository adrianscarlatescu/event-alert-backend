CREATE DATABASE  IF NOT EXISTS `event_alert` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `event_alert`;
-- MySQL dump 10.13  Distrib 8.0.20, for Win64 (x86_64)
--
-- Host: localhost    Database: event_alert
-- ------------------------------------------------------
-- Server version	5.7.25-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_time` datetime DEFAULT NULL,
  `event_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhr48nopy5aorw0ta1ii704tpu` (`event_id`),
  KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'2020-04-10 17:15:11',2,2,'Is everything fine out there?'),(2,'2020-04-10 18:44:02',2,3,'I\'ve seen that the fire passed and only one person got hurt. I will try to get in contact with someone out there.'),(3,'2020-04-10 19:23:33',2,4,'Thanks a lot. Let us know what you\'ve found out.'),(4,'2020-04-10 20:22:46',2,3,'It seems that everything is back to normal. The person that got hurt is safe now.'),(5,'2020-04-10 20:50:17',2,1,'The fire has been extinguished and everyone is OK :)'),(6,'2020-04-12 10:11:52',6,3,'Such a bad weather in Portugal... Is everyone OK?'),(7,'2020-04-12 11:25:02',6,1,'The water level is not so high. Each person is safe and no one got hurt. I think this bad weather will pass soon. Such a climate change is not usual in the region of Porto.'),(8,'2020-04-15 12:38:55',15,5,'How is it possible to snow in Sahara? Lol.'),(9,'2020-04-15 14:15:11',15,4,'Why do you laugh? I think it happened before too. Anyway, it\'s a unique phenomenon.'),(10,'2020-04-15 14:18:37',15,5,'#$@!%*@*$@!#'),(11,'2020-05-02 18:15:11',24,5,'Snow in April? Lol.');
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_time` datetime DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `severity_id` bigint(20) DEFAULT NULL,
  `tag_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfxs2162k3b01vsxw0g1lr6dqt` (`severity_id`),
  KEY `FKl3jdnin7x6bu93mpej2rlnyxr` (`tag_id`),
  KEY `FKi8bsvlthqr8lngsyshiqsodak` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES (1,'2020-04-10 09:10:00','img/event_1.jpg',44.3820459,26.171588,1,3,1,NULL),(2,'2020-04-10 13:11:16','img/event_2.jpg',44.4073963,26.1265611,1,2,1,NULL),(3,'2020-04-10 14:23:55','img/event_3.jpg',44.3196758,27.2105093,1,6,1,NULL),(4,'2020-04-10 17:47:31','img/event_4.jpg',28.212882,83.9754403,1,4,1,NULL),(5,'2020-04-11 03:13:27','img/event_5.jpg',47.8032022,22.8595308,3,20,1,NULL),(6,'2020-04-11 04:24:14','img/event_6.jpg',41.1717768,-8.6875015,3,3,1,NULL),(7,'2020-04-12 08:44:27','img/event_7.jpg',59.6081696,16.5511619,3,8,1,NULL),(8,'2020-04-12 14:52:31','img/event_8.jpg',51.4927285,-0.2003438,4,12,1,NULL),(9,'2020-04-13 11:11:55','img/event_9.jpg',50.9505312,28.6475922,4,16,1,NULL),(10,'2020-04-13 12:12:42','img/event_10.jpg',45.3410878,25.5164494,2,13,1,NULL),(11,'2020-04-13 14:30:55','img/event_11.jpg',37.7510042,14.9846801,3,10,1,NULL),(12,'2020-04-13 21:00:31','img/event_12.jpg',44.4536997,26.1206009,4,14,1,NULL),(13,'2020-04-14 16:01:16','img/event_13.jpg',44.4287873,26.1038463,1,26,1,NULL),(14,'2020-04-14 16:04:42','img/event_14.jpg',44.4345057,26.0486993,2,21,1,NULL),(15,'2020-04-14 18:22:43','img/event_15.jpg',23.2575926,26.1826986,4,16,1,NULL),(16,'2020-04-15 22:21:43','img/event_16.jpg',44.8466356,24.8753764,4,20,1,NULL),(17,'2020-04-16 04:06:52','img/event_17.jpg',44.4526384,26.0858512,4,19,1,NULL),(18,'2020-04-16 23:42:27','img/event_18.jpg',45.7593737,27.0649587,2,4,1,NULL),(19,'2020-04-18 10:11:55','img/event_19.jpg',44.4201438,26.1000533,1,2,1,NULL),(20,'2020-04-18 14:15:31','img/event_20.jpg',44.8671652,24.8496802,2,2,1,NULL),(21,'2020-04-18 15:17:16','img/event_21.jpg',44.84557,24.8882765,4,24,1,NULL),(22,'2020-04-18 20:18:42','img/event_22.jpg',44.4783304,26.1058571,4,12,1,NULL),(23,'2020-04-23 19:21:27','img/event_23.jpg',44.7120456,26.1712108,1,2,1,NULL),(24,'2020-04-23 22:02:43','img/event_24.jpg',44.6685877,24.8795494,4,16,2,NULL),(25,'2020-04-24 17:01:46','img/event_25.jpg',44.3971708,26.0570636,2,17,2,NULL),(26,'2020-05-01 13:01:22','img/event_26.jpg',39.0595653,21.8750569,2,2,3,NULL),(27,'2020-05-01 14:16:02','img/event_27.jpg',57.494195,-4.2122708,4,6,3,NULL),(28,'2020-05-02 14:32:11','img/event_28.jpg',50.0596496,19.925144,3,7,3,NULL),(29,'2020-05-02 15:26:47','img/event_29.jpg',52.0487892,29.2550429,3,8,3,NULL),(30,'2020-05-02 16:10:10','img/event_30.jpg',56.946783,23.6172156,1,9,3,NULL),(31,'2020-05-02 18:52:09','img/event_31.jpg',25.2640939,63.4767481,1,11,3,NULL),(32,'2020-05-03 08:22:09','img/event_32.jpg',46.0611305,8.4043662,4,13,3,NULL),(33,'2020-05-03 10:13:55','img/event_33.jpg',51.5148142,-0.0651437,1,18,3,NULL),(34,'2020-05-03 14:02:13','img/event_34.jpg',46.6053988,27.7682106,1,18,3,NULL),(35,'2020-05-03 15:27:25','img/event_35.jpg',47.8123253,13.0818412,3,20,4,NULL),(36,'2020-05-03 16:21:08','img/event_36.jpg',50.1123254,8.6719538,2,21,4,NULL),(37,'2020-05-03 17:12:04','img/event_37.jpg',44.7803392,20.4701967,3,23,4,NULL),(38,'2020-05-04 09:25:54','img/event_38.jpg',48.8529932,2.3499773,4,24,4,NULL),(39,'2020-05-04 11:34:15','img/event_39.jpg',41.6451143,-0.8764002,4,29,4,NULL),(40,'2020-05-04 14:01:00','img/event_40.jpg',42.6883485,23.3344281,2,26,4,NULL),(41,'2020-05-04 15:22:18','img/event_41.jpg',55.7373716,37.5287146,3,28,4,NULL),(42,'2020-05-04 16:00:28','img/event_42.jpg',44.3196796,23.8032826,4,28,4,NULL);
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `refresh_token`
--

DROP TABLE IF EXISTS `refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `refresh_token` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `refresh_token` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfgk1klcib7i15utalmcqo7krt` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `refresh_token`
--

LOCK TABLES `refresh_token` WRITE;
/*!40000 ALTER TABLE `refresh_token` DISABLE KEYS */;
INSERT INTO `refresh_token` VALUES (9,'eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJSZWZyZXNoVG9rZW5JZCIsInN1YiI6InRlc3QxQHRlc3QuY29tIiwiaWF0IjoxNTg5MDExMDgwLCJleHAiOjE1ODk0NDMwODB9.DALy0AxgiPFN41eqQoAaGUIomJ5HiCFUo1ydyfo5VXjJ-fnG8rPs8ibVK3567pMfWbrtMdgYBeb664vJN5E4uQ',1);
/*!40000 ALTER TABLE `refresh_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_USER'),(2,'ROLE_ADMIN');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `severity`
--

DROP TABLE IF EXISTS `severity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `severity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) DEFAULT NULL,
  `color` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `severity`
--

LOCK TABLES `severity` WRITE;
/*!40000 ALTER TABLE `severity` DISABLE KEYS */;
INSERT INTO `severity` VALUES (1,'Critical',16711680),(2,'Major',16745779),(3,'Minor',16762980),(4,'Trivial',11192420);
/*!40000 ALTER TABLE `severity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `image_path` varchar(255) DEFAULT NULL,
  `name` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (1,NULL,'Other'),(2,'img/tag_fire.png','Fire'),(3,'img/tag_flood.png','Flood'),(4,'img/tag_earthquake.png','Earthquake'),(5,'img/tag_landslide.png','Landslide'),(6,'img/tag_storm.png','Storm'),(7,'img/tag_heavy_rain.png','Heavy rain'),(8,'img/tag_intense_snow.png','Snow abundance'),(9,'img/tag_hurricane.png','Hurricane'),(10,'img/tag_volcanic_erruption.png','Volcanic eruption'),(11,'img/tag_tsunami.png','Tsunami'),(12,'img/tag_fog.png','Dense fog'),(13,'img/tag_avalanche.png','Avalanche'),(14,'img/tag_ground_hole.png','Ground hole'),(15,'img/tag_high_temperature.png','High temperature'),(16,'img/tag_low_temperature.png','Low temperature'),(17,'img/tag_fight.png','Fight'),(18,'img/tag_murder.png','Murder'),(19,'img/tag_protest.png','Protest'),(20,'img/tag_traffic_accident.png','Traffic accident'),(21,'img/tag_industrial_accident.png','Industrial accident'),(22,'img/tag_dangerous_animal.png','Dangerous animal'),(23,'img/tag_explosion.png','Explosion'),(24,'img/tag_dangerous_building.png','Dangerous building'),(25,'img/tag_electrical_shock.png','Electrical shock'),(26,'img/tag_intense_pollution.png','Intense pollution'),(27,'img/tag_blocked_road.png','Blocked road'),(28,'img/tag_vandalism.png','Vandalism'),(29,'img/tag_nuclear_radiation.png','Nuclear radiation'),(30,'img/tag_gunfire.png','Gunfire');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `join_date_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'1984-05-23','test1@test.com','Alan','MALE','img/user_1.jpg','Walter','$2y$12$0cKtpBZT8N1f1pbUu65bPerBT.bbo6Lur.2asTHfabZnOzJ5Wl3pe','+03442777999','2020-05-07 19:35:40'),(2,'1987-10-16','test2@test.com','John','MALE','img/user_2.jpg','Smith','$2y$12$0cKtpBZT8N1f1pbUu65bPerBT.bbo6Lur.2asTHfabZnOzJ5Wl3pe','+44627779991','2020-05-07 19:37:49'),(3,'1990-09-27','test3@test.com','Cindy','FEMALE','img/user_3.jpg','Milner','$2y$12$0cKtpBZT8N1f1pbUu65bPerBT.bbo6Lur.2asTHfabZnOzJ5Wl3pe','+44848992441','2020-05-07 19:39:51'),(5,'1991-06-12','test5@test.com','Amy','FEMALE','img/user_5.jpg','Patterson','$2y$12$0cKtpBZT8N1f1pbUu65bPerBT.bbo6Lur.2asTHfabZnOzJ5Wl3pe','+44226812555','2020-05-08 10:39:14'),(4,'1992-02-14','test4@test.com','Andrew','MALE','img/user_4.jpg','Carter','$2y$12$0cKtpBZT8N1f1pbUu65bPerBT.bbo6Lur.2asTHfabZnOzJ5Wl3pe','+44226812612','2020-05-08 10:39:14');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKt4v0rrweyk393bdgt107vdx0x` (`role_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES (1,1),(1,2),(2,1),(3,1),(4,1),(5,1);
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-09 11:15:03
