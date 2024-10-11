ALTER TABLE users ALTER COLUMN subscription_amount SET DEFAULT 0;

UPDATE users SET subscription_amount = 0 WHERE subscription_amount IS NULL;