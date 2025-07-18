CREATE TABLE IF NOT EXISTS friendships (
    id BIGSERIAL PRIMARY KEY,
    friend1_id BIGINT REFERENCES users(id) NOT NULL,
    friend2_id BIGINT REFERENCES users(id) NOT NULL,
    friends_since TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE UNIQUE INDEX friendships_pair_idx ON friendships (friend1_id, friend2_id);