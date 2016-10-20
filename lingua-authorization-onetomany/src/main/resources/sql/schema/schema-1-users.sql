DROP TABLE IF EXISTS user_authority;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30) NOT NULL,
    password BINARY(60) NOT NULL,
    account_non_expired BOOLEAN NOT NULL,
    account_non_locked BOOLEAN NOT NULL,
    credentials_non_expired BOOLEAN NOT NULL,
    enabled BOOLEAN NOT NULL,
    UNIQUE KEY uk_username (Username)
) ENGINE = InnoDB;

CREATE TABLE user_authority (
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    users_id BIGINT UNSIGNED NOT NULL,
    authority VARCHAR(100) NOT NULL,
    UNIQUE KEY uk_users_id_authority (users_id, authority),
    CONSTRAINT fk_users_id FOREIGN KEY (users_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE = InnoDB;