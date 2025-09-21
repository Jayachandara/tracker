--liquibase formatted sql

--changeset nandana-expense:1
-- category_group table
CREATE TABLE category_group (
    category_group_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    CONSTRAINT fk_group_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    UNIQUE KEY unique_group_per_user (user_id, name)
);

-- category_type table
CREATE TABLE category_type (
    category_type_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    category_group_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    CONSTRAINT fk_type_group FOREIGN KEY (category_group_id) REFERENCES category_group(category_group_id) ON DELETE CASCADE,
    CONSTRAINT fk_type_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    UNIQUE KEY unique_type_per_user (user_id, name)
);

-- category table
CREATE TABLE category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    category_type_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    CONSTRAINT fk_category_type FOREIGN KEY (category_type_id) REFERENCES category_type(category_type_id) ON DELETE CASCADE,
    CONSTRAINT fk_category_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    UNIQUE KEY unique_category_per_user (user_id, name)
);

-- payment_type table
CREATE TABLE payment_type (
    payment_type_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255),
    CONSTRAINT fk_payment_user FOREIGN KEY (user_id) REFERENCES users(user_id),
    UNIQUE KEY unique_payment_per_user (user_id, name)
);

-- transactions table
CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    category_id INT NOT NULL,
    payment_type_id INT NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    description VARCHAR(255),
    CONSTRAINT fk_transaction_category FOREIGN KEY (category_id) REFERENCES category(category_id) ON DELETE CASCADE,
    CONSTRAINT fk_transaction_payment FOREIGN KEY (payment_type_id) REFERENCES payment_type(payment_type_id) ON DELETE CASCADE,
    CONSTRAINT fk_transaction_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);