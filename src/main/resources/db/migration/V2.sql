/*Table structure for table `question` */

DROP TABLE IF EXISTS `question`;

CREATE TABLE `question` (
                            `id` INT NOT NULL AUTO_INCREMENT,
                            `title` VARCHAR(50) DEFAULT NULL,
                            `description` TEXT,
                            `gmt_create` BIGINT DEFAULT NULL,
                            `gmt_modified` BIGINT DEFAULT NULL,
                            `creator` INT DEFAULT NULL,
                            `comment_count` INT DEFAULT '0',
                            `view_count` INT DEFAULT '0',
                            `like_count` INT DEFAULT NULL,
                            `tag` VARCHAR(256) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT  INTO `question`(`id`,`title`,`description`,`gmt_create`,`gmt_modified`,`creator`,`comment_count`,`view_count`,`like_count`,`tag`) VALUES
(3,'aaa','aaaaaa',1611404955518,1611404955518,5,0,0,0,'aaa'),
(4,'fas','GDA',1611405276286,1611405276286,5,0,0,0,'gfSGA');
