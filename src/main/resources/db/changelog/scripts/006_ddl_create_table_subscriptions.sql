CREATE TABLE IF NOT EXISTS subscriptions (
    id BIGSERIAL PRIMARY KEY,
    follower_id BIGINT REFERENCES users(id) NOT NULL,
    followed_id BIGINT REFERENCES users(id) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW(),
    is_subscribed BOOLEAN NOT NULL
);