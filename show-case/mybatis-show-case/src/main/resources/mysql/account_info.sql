CREATE TABLE account_info (
	id varchar(36) NOT NULL primary key,
    user_id varchar(36) NOT NULL,
	num varchar(100) NOT NULL,
	amount INT DEFAULT 0 NOT NULL,
	last_modify_date DATETIME NOT NULL
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;