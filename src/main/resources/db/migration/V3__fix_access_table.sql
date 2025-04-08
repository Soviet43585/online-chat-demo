ALTER TABLE `chat-demo`.`user_company_access_queue`
DROP FOREIGN KEY `user_company_access_queue_company_id_Company_id`,
DROP FOREIGN KEY `user_company_access_queue_user_id_User_id`;
ALTER TABLE `chat-demo`.`user_company_access_queue`
CHANGE COLUMN `user_id` `user_id` CHAR(36) NOT NULL ,
CHANGE COLUMN `company_id` `company_id` CHAR(36) NOT NULL ;
ALTER TABLE `chat-demo`.`user_company_access_queue`
ADD CONSTRAINT `user_company_access_queue_company_id_Company_id`
  FOREIGN KEY (`company_id`)
  REFERENCES `chat-demo`.`company` (`id`),
ADD CONSTRAINT `user_company_access_queue_user_id_User_id`
  FOREIGN KEY (`user_id`)
  REFERENCES `chat-demo`.`user` (`id`);