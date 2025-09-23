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
    category_type VARCHAR(100) NOT NULL,
    category_group VARCHAR(100) NOT NULL,
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

-- Table for User Loans

CREATE TABLE user_loans (
    loan_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,

    loan_type VARCHAR(50) NOT NULL,       -- Personal, Gold, Vehicle, Home, etc.
    loan_name VARCHAR(100),               -- Friendly identifier (e.g., "HDFC Loan #1")
    lender_name VARCHAR(100),             -- Bank/NBFC name

    loan_amount DECIMAL(15,2) NOT NULL,
    interest_rate DECIMAL(5,2) NOT NULL,  -- annual interest %
    tenure_months INT NOT NULL,           -- total months
    emi_amount DECIMAL(15,2),             -- pre-calculated EMI

    start_date DATE NOT NULL,
    status VARCHAR(100) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Table for Loan Transactions

CREATE TABLE loan_transactions (
    loan_txn_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan_id BIGINT NOT NULL,
    transaction_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_loan FOREIGN KEY (loan_id) REFERENCES user_loans(loan_id),
    CONSTRAINT fk_transaction FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id),
    UNIQUE (loan_id, transaction_id)
);

-- Table for Lona Transactions

CREATE OR REPLACE VIEW loan_payment_summary AS
SELECT
    l.loan_id,
    l.user_id,
    l.loan_type,
    l.loan_name,
    l.lender_name,
    l.loan_amount,
    l.interest_rate,
    l.tenure_months,
    l.emi_amount,
    l.start_date,
    l.status,

    COUNT(t.transaction_id) AS months_paid,
    COALESCE(SUM(t.amount), 0) AS total_paid,
    (l.tenure_months - COUNT(t.transaction_id)) AS remaining_months,
    (l.loan_amount - COALESCE(SUM(t.amount), 0)) AS remaining_balance,

    CASE
        WHEN COUNT(t.transaction_id) >= l.tenure_months THEN NULL
        ELSE DATE_ADD(l.start_date, INTERVAL (COUNT(t.transaction_id) + 1) MONTH)
    END AS next_due_date

FROM user_loans l
LEFT JOIN loan_transactions lt ON l.loan_id = lt.loan_id
LEFT JOIN transactions t ON lt.transaction_id = t.transaction_id
GROUP BY
    l.loan_id, l.user_id, l.loan_type, l.loan_name, l.lender_name,
    l.loan_amount, l.interest_rate, l.tenure_months, l.emi_amount,
    l.start_date, l.status;


