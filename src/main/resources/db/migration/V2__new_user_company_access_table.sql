CREATE TABLE user_company_access_queue (
user_id VARCHAR(32) PRIMARY KEY NOT NULL UNIQUE,
company_id VARCHAR(32) NOT NULL,
is_available BOOLEAN NOT NULL DEFAULT 0);

ALTER TABLE user_company_access_queue ADD CONSTRAINT user_company_access_queue_user_id_User_id FOREIGN KEY (user_id) REFERENCES User(id);
ALTER TABLE user_company_access_queue ADD CONSTRAINT user_company_access_queue_company_id_Company_id FOREIGN KEY (company_id) REFERENCES Company(id);