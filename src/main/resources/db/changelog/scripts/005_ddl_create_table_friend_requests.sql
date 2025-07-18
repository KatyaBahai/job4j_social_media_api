CREATE TABLE IF NOT EXISTS friend_requests (
    id BIGSERIAL PRIMARY KEY,
    sender_id BIGINT REFERENCES users(id) NOT NULL,
    receiver_id BIGINT REFERENCES users(id) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    status VARCHAR(255) NOT NULL
);