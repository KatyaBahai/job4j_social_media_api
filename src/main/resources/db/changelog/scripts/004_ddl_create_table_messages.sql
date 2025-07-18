CREATE TABLE IF NOT EXISTS messages (
    id BIGSERIAL PRIMARY KEY,
    sender_id BIGINT REFERENCES users(id) NOT NULL,
    recipient_id BIGINT REFERENCES users(id) NOT NULL,
    text VARCHAR(10000),
    sent_at TIMESTAMP DEFAULT NOW()
);