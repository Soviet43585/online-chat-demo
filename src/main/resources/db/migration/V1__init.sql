CREATE TABLE `User`
(
 `id`       int NOT NULL AUTO_INCREMENT ,
 `login`    nvarchar(50) NOT NULL UNIQUE ,
 `password` nvarchar(100) NOT NULL ,
 `email`    nvarchar(100) NOT NULL UNIQUE ,
 `role`     nvarchar(50) NOT NULL DEFAULT 'ROLE_USER',

PRIMARY KEY auto_increment(`id`),
CONSTRAINT verify_role CHECK ( role IN ('ROLE_ADMIN', 'ROLE_USER'))
);