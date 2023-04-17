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
  `is_return` TINYINT DEFAULT 0,
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
INSERT INTO `book` VALUES ('Life 3.0 - Loài người trong kỷ nguyên trí tuệ nhân tạo','Max Tegmark','-Sự sống 1.0: Giai đoạn sinh học -Sự sống 2.0: Giai đoạn văn hóa  -Sự sống 3.0: Giai đoạn công nghệ  Trí tuệ nhân tạo ảnh hưởng như thế nào đến công ăn việc làm, tội phạm, chiến tranh và mọi mặt của đời sống con người?  Loài người có thể làm gì để phát triển thịnh vượng nhờ tự động hóa mà không rơi vào nghèo khó và sống thiếu mục đích?  Liệu cuối cùng máy móc có vượt qua cả trí tuệ nhân loại để thay thế hoàn toàn con người?  Cuốn sách này sẽ dẫn dắt bạn qua những câu hỏi lớn kể trên, để cuối cùng đến với vấn đề quan trọng bậc nhất của thời đại ngày nay: BẠN MUỐN TƯƠNG LAI CỦA CHÚNG TA NHƯ THẾ NÀO?  +VỀ TÁC GIẢ:  Max Tegmark là giáo sư khoa Vật lý của Viện Công Nghệ Massachusetts (MIT) kiêm chủ tịch Viện Tương lai Sự sống. Ông là tác giả cuốn Our Mathematical Universe (Vũ trụ toán học của chúng ta) và là khách mời xuất hiện trong hàng chục phim tài liệu khoa học. Niềm đam mê của ông với các ý tưởng, sự phiêu lưu và một tương lai đầy cảm hứng có sức lan tỏa lớn trong cộng đồng.',2023,'USA','2023-03-10','001.10',2,'FREE')
INSERT INTO `librarymanagement`.`book` (`id`, `title`, `authors`, `description`, `publish_year`, `publish_place`, `import_date`, `location`, `category_id`, `state`) VALUES ('4', 'Nhà giả kim', 'Paulo Coelho', 'Truyện - tiểu thuyết', '1988', 'Brazil', '2022-10-20', '346.44', '6', 'FREE');
INSERT INTO `librarymanagement`.`book` (`id`, `title`, `authors`, `description`, `publish_year`, `publish_place`, `import_date`, `location`, `category_id`, `state`) VALUES ('5', 'Đắc nhân tâm', 'Dale Carnegie', 'Kinh tế', '1936', 'Mỹ', '2020-11-20', '236.12', '3', 'FREE');
INSERT INTO `librarymanagement`.`book` (`id`, `title`, `authors`, `description`, `publish_year`, `publish_place`, `import_date`, `location`, `category_id`, `state`) VALUES ('6', 'Cách nghĩ để thành công', 'Napoleon Hill', 'Kinh tế', '1930', 'Mỹ', '2021-01-23', '123.23', '3', 'FREE');
INSERT INTO `librarymanagement`.`book` (`id`, `title`, `authors`, `description`, `publish_year`, `publish_place`, `import_date`, `location`, `category_id`, `state`) VALUES ('7', 'Hạt giống tâm hồn', 'Nhiều tác giả', 'Văn hóa xã hội', '1945', 'Anh', '2012-12-23', '123.12', '7', 'FREE');
INSERT INTO `librarymanagement`.`book` (`id`, `title`, `authors`, `description`, `publish_year`, `publish_place`, `import_date`, `location`, `category_id`, `state`) VALUES ('8', 'Quẳng gánh lo đi và vui sống', 'Dale Carnegie', 'Văn hóa xã hộI', '1990', 'Pháp', '2013-03-10', '243.53', '7', 'FREE');
INSERT INTO `librarymanagement`.`book` (`id`, `title`, `authors`, `description`, `publish_year`, `publish_place`, `import_date`, `location`, `category_id`, `state`) VALUES ('9', 'Tiểu thuyết Bố Già', 'Mario Puzo', 'Truyện - tiểu thuyết', '1969', 'Ý', '2023-04-10', '123.45', '6', 'FREE');
INSERT INTO `librarymanagement`.`book` (`id`, `title`, `authors`, `description`, `publish_year`, `publish_place`, `import_date`, `location`, `category_id`, `state`) VALUES ('10', '12 Xu Hướng Công Nghệ Trong Thời Đại 4.0', 'Kevin Kelly', 'Khoa học công nghệ', '2000', 'Pháp', '2023-01-23', '123.23', '2', 'FREE');
INSERT INTO `librarymanagement`.`book` (`id`, `title`, `authors`, `description`, `publish_year`, `publish_place`, `import_date`, `location`, `category_id`, `state`) VALUES ('11', 'Khoa Học Khám Phá – Dữ Liệu Lớn', 'Nhiều Tác Giả', 'Khoa học công nghệ', '2001', 'Tây Ban Nha', '2022-01-12', '1231.1', '2', 'FREE');
INSERT INTO `librarymanagement`.`book` (`id`, `title`, `authors`, `description`, `publish_year`, `publish_place`, `import_date`, `location`, `category_id`, `state`) VALUES ('12', 'Câu Chuyện Nghệ Thuật', 'Susie Hodge', 'Văn học nghệ thuật', '1989', 'Bồ Đào Nha', '2021-02-11', '12.12', '4', 'FREE');
INSERT INTO `librarymanagement`.`book` (`id`, `title`, `authors`, `description`, `publish_year`, `publish_place`, `import_date`, `location`, `category_id`, `state`) VALUES ('13', 'Nghệ Thuật – Khái Lược Những Tư Tưởng Lớn', 'DK', 'Văn học nghệ thuật', '1970', 'Anh', '2020-01-09', '123.54', '4', 'FREE');
INSERT INTO `librarymanagement`.`book` (`id`, `title`, `authors`, `description`, `publish_year`, `publish_place`, `import_date`, `location`, `category_id`, `state`) VALUES ('14', 'Đại Việt Sử Ký Toàn Thư', 'Đào Duy Anh', 'Lịch sử', '2022', 'Việt Nam', '2023-01-23', '12.98', '8', 'FREE');
INSERT INTO `librarymanagement`.`book` (`id`, `title`, `authors`, `description`, `publish_year`, `publish_place`, `import_date`, `location`, `category_id`, `state`) VALUES ('15', 'Võ Thị Sáu - Con Người Và Huyền Thoại', 'Nguyễn Đình Thống', 'Lịch sử', '2023', 'Việt Nam', '2023-02-10', '12312.43', '8 ', 'FREE');
INSERT INTO `librarymanagement`.`book` (`id`, `title`, `authors`, `description`, `publish_year`, `publish_place`, `import_date`, `location`, `category_id`, `state`) VALUES ('16', 'Hoàng Tử Bé', 'Antoine De Saint-Exupéry', 'Sách thiếu nhi', '2012', 'Ý', '2012-05-05', '127.87', '5', 'FREE');
INSERT INTO `librarymanagement`.`book` (`id`, `title`, `authors`, `description`, `publish_year`, `publish_place`, `import_date`, `location`, `category_id`, `state`) VALUES ('17', 'Tôi Vẽ - Phương Pháp Tự Học Vẽ Truyện Tranh', 'Nhiều tác giả', 'Sách thiếu nhi', '2010', 'Nhật Bản', '2014-05-04', '87.65', '5', 'FREE');


