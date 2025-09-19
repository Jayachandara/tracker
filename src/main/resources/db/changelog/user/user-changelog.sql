
--liquibase formatted sql

--changeset nandana-user:1
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    google_id VARCHAR(150),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
