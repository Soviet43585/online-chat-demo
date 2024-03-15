CREATE TABLE `template-users`.`verification_code` (
  `email` VARCHAR(100) NOT NULL UNIQUE,
  `code` VARCHAR(6) NOT NULL,
  `is_verified` TINYINT NOT NULL DEFAULT 0,
  PRIMARY KEY (`email`));