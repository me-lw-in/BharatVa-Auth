CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       phone_number VARCHAR(15) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       refresh_token VARCHAR(255),
                       otp VARCHAR(10),
                       otp_expiry TIMESTAMP NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
