-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: localhost    Database: librarymanagement
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `authors` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `publish_year` int NOT NULL,
  `publish_place` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `import_date` date NOT NULL,
  `location` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `category_id` int NOT NULL,
  `state` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_book_category` (`category_id`),
  CONSTRAINT `fk_book_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'Quyền Lập Pháp','Nguyen Dang Dung','Pháp luật - Chính trị',2023,'Hà Nội','2023-03-10','342.01',1,'FREE'),(2,'Pháp Luật Đại Cương','Bui Ngoc Tuyen, Pham Thanh Tu','Pháp luật - Chính trị',2022,'Hà Nội','2022-09-20','340.03',1,'FREE'),(3,'Luật tố tụng dân sự Việt Nam','Dang Thanh Hoa, Bui Anh Thuc Doan','Pháp luật - Chính trị',2022,'Hà Nội','2022-12-31','346.43',1,'FREE'),(4,'f','f','f',2022,'f','2023-04-17','f',3,'FREE'),(5,'f','f','f',2013,'f','2023-04-17','f',5,'FREE'),(6,'f','f','f',1999,'f','2023-04-17','f',6,'FREE');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrow_detail`
--

DROP TABLE IF EXISTS `borrow_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrow_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `borrow_date` date NOT NULL,
  `return_date` date DEFAULT NULL,
  `user_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `book_id` int NOT NULL,
  `is_return` tinyint DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `fk_borrow_user` (`user_id`),
  KEY `fk_borrow_book` (`book_id`),
  CONSTRAINT `fk_borrow_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `fk_borrow_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrow_detail`
--

LOCK TABLES `borrow_detail` WRITE;
/*!40000 ALTER TABLE `borrow_detail` DISABLE KEYS */;
INSERT INTO `borrow_detail` VALUES (1,'2023-04-17','2023-05-01','Blfyr8UfJU6nAtgMxB89',1,1),(2,'2023-04-17','2023-05-01','qVXPHHxd01xEDp0C2Q4g',2,1),(3,'2023-04-17','2023-05-01','qVXPHHxd01xEDp0C2Q4g',1,1),(4,'2023-04-17','2023-05-01','Blfyr8UfJU6nAtgMxB89',2,1),(5,'2023-04-17','2023-05-01','Blfyr8UfJU6nAtgMxB89',1,1),(6,'2023-04-17','2023-05-01','Blfyr8UfJU6nAtgMxB89',2,1),(7,'2023-04-17','2023-05-01','qVXPHHxd01xEDp0C2Q4g',1,1),(8,'2023-04-17','2023-05-01','qVXPHHxd01xEDp0C2Q4g',2,1),(9,'2023-04-17','2023-05-01','Blfyr8UfJU6nAtgMxB89',1,1),(10,'2023-04-17','2023-05-01','Blfyr8UfJU6nAtgMxB89',2,1),(11,'2023-04-17','2023-05-01','Blfyr8UfJU6nAtgMxB89',3,1),(12,'2023-04-17','2023-05-01','Blfyr8UfJU6nAtgMxB89',4,1),(13,'2023-04-17','2023-05-01','Blfyr8UfJU6nAtgMxB89',5,1),(14,'2023-04-17','2023-05-01','Blfyr8UfJU6nAtgMxB89',1,1),(15,'2023-04-17','2023-05-01','hVvDhuacHJo9f38po8KU',4,1),(16,'2023-04-17','2023-05-01','Blfyr8UfJU6nAtgMxB89',1,1),(17,'2023-04-17','2023-05-01','qVXPHHxd01xEDp0C2Q4g',5,1);
/*!40000 ALTER TABLE `borrow_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Chính trị - pháp luật'),(2,'Khoa học công nghệ'),(3,'Kinh tế'),(4,'Văn học nghệ thuật'),(5,'Sách thiếu nhi'),(6,'Truyện - tiểu thuyết'),(7,'Văn hóa xã hội'),(8,'Lịch sử');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faculty` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty`
--

LOCK TABLES `faculty` WRITE;
/*!40000 ALTER TABLE `faculty` DISABLE KEYS */;
INSERT INTO `faculty` VALUES (1,'Tài chính - ngân hàng'),(2,'Công nghệ thông tin'),(3,'Quản trị kinh doanh'),(4,'Xây dựng'),(5,'Kĩ thuật điện tử'),(6,'Ngôn ngữ Anh');
/*!40000 ALTER TABLE `faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `library_card`
--

DROP TABLE IF EXISTS `library_card`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `library_card` (
  `id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `gender` tinyint NOT NULL,
  `birth_date` date NOT NULL,
  `email` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `expire` date DEFAULT NULL,
  `address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone_number` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `subject` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `faculty_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_card_faculty` (`faculty_id`),
  CONSTRAINT `fk_card_faculty` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `library_card`
--

LOCK TABLES `library_card` WRITE;
/*!40000 ALTER TABLE `library_card` DISABLE KEYS */;
INSERT INTO `library_card` VALUES ('1jT4d6GUdtCHnL5qkq7e','Nguyễn Văn Anh',1,'2001-05-31','Anh@gmail.com','2024-04-14','Trung An, Củ Chi, TPHCM','0567895432','STUDENT',2),('eTvP0uwg7RFxcPXThDCp','Nguyễn Thị Lan',2,'1997-07-28','Lan@yahoo.com','2025-05-28','Phường 5, Gò Vấp, TPHCM','0953427899','ADMIN',1),('RkljzD4Ai4TYgDncFY43','Hoàng Thị Nghỉ',2,'1998-02-28','Nghi@gmail.com','2025-09-30','Chánh Mỹ, Thủ Dầu Một, Bình Dương','0876987456','TEACHER',5);
/*!40000 ALTER TABLE `library_card` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_date` datetime NOT NULL,
  `book_id` int NOT NULL,
  `user_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_reservation_book` (`book_id`),
  KEY `fk_reservation_user` (`user_id`),
  CONSTRAINT `fk_reservation_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `fk_reservation_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `card_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `card_id_UNIQUE` (`card_id`),
  CONSTRAINT `fk_card_user` FOREIGN KEY (`card_id`) REFERENCES `library_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('Blfyr8UfJU6nAtgMxB89','anh.nv','abc123!','1jT4d6GUdtCHnL5qkq7e'),('hVvDhuacHJo9f38po8KU','lan.nt','Admin@123','eTvP0uwg7RFxcPXThDCp'),('qVXPHHxd01xEDp0C2Q4g','n.ht','abc321!','RkljzD4Ai4TYgDncFY43');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-17 13:53:12
