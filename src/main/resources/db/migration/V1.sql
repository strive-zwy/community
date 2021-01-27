/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `account_id` VARCHAR(300) DEFAULT NULL,
  `name` VARCHAR(150) DEFAULT NULL,
  `bio` VARCHAR(300) DEFAULT NULL,
  `token` VARCHAR(108) DEFAULT NULL,
  `gmt_create` BIGINT DEFAULT NULL,
  `gmt_modified` BIGINT DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

INSERT  INTO `user`(`id`,`account_id`,`name`,`bio`,`token`,`gmt_create`,`gmt_modified`) VALUES
(1,'47515705','张婉玉','000','3b777d22-d04f-4c67-b834-e2b30db52017',1611234414044,1611234414044);

ALTER TABLE user ADD COLUMN `avatar_url` VARCHAR(100) NULL AFTER gmt_modified;
