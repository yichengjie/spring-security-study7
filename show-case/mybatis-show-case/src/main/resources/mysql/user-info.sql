CREATE TABLE user_info (
  id varchar(36) NOT NULL,
  username varchar(100) NOT NULL,
  password varchar(100) NOT NULL,
  address varchar(100) DEFAULT NULL,
  PRIMARY KEY (id)
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;