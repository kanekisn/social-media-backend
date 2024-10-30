ALTER TABLE chats
    ADD COLUMN last_message VARCHAR(255),
    ADD COLUMN last_msg_time TIMESTAMP;