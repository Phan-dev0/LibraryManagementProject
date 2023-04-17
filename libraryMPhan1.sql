CREATE DATABASE librarymanagement DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
USE librarymanagement;

CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `authors` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `faculty` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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

CREATE TABLE `users` (
  `id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `card_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `card_id_UNIQUE` (`card_id`),
  CONSTRAINT `fk_card_user` FOREIGN KEY (`card_id`) REFERENCES `library_card` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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

CREATE TABLE `borrow_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `borrow_date` date NOT NULL,
  `return_date` date DEFAULT NULL,
  `user_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `book_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_borrow_user` (`user_id`),
  KEY `fk_borrow_book` (`book_id`),
  CONSTRAINT `fk_borrow_book` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`),
  CONSTRAINT `fk_borrow_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

INSERT INTO `category` VALUES (1,'Chính trị - pháp luật'),(2,'Khoa học công nghệ'),(3,'Kinh tế'),(4,'Văn học nghệ thuật'),(5,'Sách thiếu nhi'),(6,'Truyện - tiểu thuyết'),(7,'Văn hóa xã hội'),(8,'Lịch sử');
INSERT INTO `faculty` VALUES (1,'Tài chính - ngân hàng'),(2,'Công nghệ thông tin'),(3,'Quản trị kinh doanh'),(4,'Xây dựng'),(5,'Kĩ thuật điện tử'),(6,'Ngôn ngữ Anh');
INSERT INTO `book` VALUES (1,'Quyền Lập Pháp','Nguyen Dang Dung','Pháp luật - Chính trị',2023,'Hà Nội','2023-03-10','342.01',1,'FREE'),(2,'Pháp Luật Đại Cương','Bui Ngoc Tuyen, Pham Thanh Tu','Pháp luật - Chính trị',2022,'Hà Nội','2022-09-20','340.03',1,'FREE'),(3,'Luật tố tụng dân sự Việt Nam','Dang Thanh Hoa, Bui Anh Thuc Doan','Pháp luật - Chính trị',2022,'Hà Nội','2022-12-31','346.43',1,'BORROWED');
INSERT INTO `library_card` VALUES ('1jT4d6GUdtCHnL5qkq7e','Nguyễn Văn Anh',1,'2001-05-31','Anh@gmail.com','2024-04-14','Trung An, Củ Chi, TPHCM','0567895432','STUDENT',2),('eTvP0uwg7RFxcPXThDCp','Nguyễn Thị Lan',2,'1997-07-28','Lan@yahoo.com','2025-05-28','Phường 5, Gò Vấp, TPHCM','0953427899','ADMIN',1),('RkljzD4Ai4TYgDncFY43','Hoàng Thị Nghỉ',2,'1998-02-28','Nghi@gmail.com','2025-09-30','Chánh Mỹ, Thủ Dầu Một, Bình Dương','0876987456','TEACHER',5);
INSERT INTO `users` VALUES ('Blfyr8UfJU6nAtgMxB89','anh.nv','abc123!','1jT4d6GUdtCHnL5qkq7e'),('hVvDhuacHJo9f38po8KU','lan.nt','Admin@123','eTvP0uwg7RFxcPXThDCp'),('qVXPHHxd01xEDp0C2Q4g','n.ht','abc321!','RkljzD4Ai4TYgDncFY43');
