CREATE SEQUENCE users_seq START 1;

CREATE TABLE users (
            id BIGINT PRIMARY KEY DEFAULT nextval('users_seq'),
            first_name VARCHAR(255),
            last_name VARCHAR(255),
            avatar_url VARCHAR(255),
            city VARCHAR(255),
            subscription_amount BIGINT,
            username VARCHAR(100) UNIQUE NOT NULL,
            password VARCHAR(255) NOT NULL,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_stack (
            user_id BIGINT,
            stack_item VARCHAR(255),
            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);