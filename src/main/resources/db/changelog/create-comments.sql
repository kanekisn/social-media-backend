CREATE TABLE comments (
      id BIGSERIAL PRIMARY KEY,
      post_id BIGINT NOT NULL,
      user_id BIGINT NOT NULL,
      content TEXT NOT NULL,
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      CONSTRAINT fk_post FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
      CONSTRAINT fk_user_comment FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
