CREATE SEQUENCE IF NOT EXISTS chats_id_seq START 1;

CREATE TABLE IF NOT EXISTS chats (
                             id BIGINT PRIMARY KEY DEFAULT nextval('chats_id_seq')
);

CREATE TABLE IF NOT EXISTS chat_participants (
                             chat_id BIGINT NOT NULL,
                             user_id BIGINT NOT NULL,
                             CONSTRAINT fk_chat_participants_chat FOREIGN KEY (chat_id) REFERENCES chats(id),
                             CONSTRAINT fk_chat_participants_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE SEQUENCE IF NOT EXISTS messages_id_seq START 1;

CREATE TABLE IF NOT EXISTS messages (
                            id BIGINT PRIMARY KEY DEFAULT nextval('messages_id_seq'),
                            content TEXT NOT NULL,
                            timestamp TIMESTAMP NOT NULL DEFAULT NOW(),
                            chat_id BIGINT NOT NULL,
                            sender_id BIGINT NOT NULL,
                            CONSTRAINT fk_messages_chat FOREIGN KEY (chat_id) REFERENCES chats(id),
                            CONSTRAINT fk_messages_sender FOREIGN KEY (sender_id) REFERENCES users(id)
);