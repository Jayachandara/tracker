--liquibase formatted sql

--changeset nandana-expense:1
-- Table for CategoryType
CREATE TABLE category_type (
    category_type_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL
);

-- Table for Category
CREATE TABLE category (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    user_id BIGINT NOT NULL,
    category_type_id BIGINT NOT NULL,
    category_group VARCHAR(100) NULL,
    CONSTRAINT fk_category_category_type
        FOREIGN KEY (category_type_id)
        REFERENCES category_type(category_type_id)
);
-- Table for Transactions
CREATE TABLE transactions (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE NOT NULL,
    transaction_date DATETIME NOT NULL,
    category_id BIGINT NOT NULL,
    payment_type VARCHAR(100) NULL,
    description VARCHAR(255) NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_transaction_category
        FOREIGN KEY (category_id)
        REFERENCES category(category_id)
);