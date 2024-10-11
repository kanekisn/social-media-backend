CREATE TABLE subscriptions (
                               follower_id BIGINT NOT NULL,
                               followed_id BIGINT NOT NULL,
                               PRIMARY KEY (follower_id, followed_id),
                               CONSTRAINT fk_subscriptions_follower
                                   FOREIGN KEY (follower_id) REFERENCES users(id) ON DELETE CASCADE,
                               CONSTRAINT fk_subscriptions_followed
                                   FOREIGN KEY (followed_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_follower_id ON subscriptions(follower_id);
CREATE INDEX idx_followed_id ON subscriptions(followed_id);