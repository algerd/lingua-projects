-- admin:password
INSERT INTO users (username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES ('admin', '$2a$10$x0k/yA5qN8SP8JD5CEN.6elEBFxVVHeKZTdyv.RPra4jzRR5SlKSC', TRUE, TRUE, TRUE, TRUE);
-- user:green
INSERT INTO users (username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled)
VALUES ('user', '$2a$10$vacuqbDw9I7rr6RRH8sByuktOzqTheQMfnK3XCT2WlaL7vt/3AMby', TRUE, TRUE, TRUE, TRUE);

INSERT INTO user_authority (users_id, authority) VALUES (1, 'ADMIN'), (1, 'USER');
INSERT INTO user_authority (users_id, authority) VALUES (2, 'USER');