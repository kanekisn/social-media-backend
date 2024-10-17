CREATE TABLE likes (
                       id BIGSERIAL PRIMARY KEY,
                       user_id BIGINT NOT NULL,
                       post_id BIGINT NOT NULL,
                       liked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
                       CONSTRAINT fk_post FOREIGN KEY (post_id) REFERENCES posts(id),
                       CONSTRAINT uc_user_post UNIQUE (user_id, post_id)
);