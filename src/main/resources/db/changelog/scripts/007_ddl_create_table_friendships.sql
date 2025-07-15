CREATE TABLE if not exists friendships (
    id BIGSERIAL PRIMARY KEY,
    friend1_id BIGINT references users(id) NOT NULL,
    friend2_id BIGINT references users(id) NOT NULL,
    friends_since timestamp NOT NULL default now()
);

CREATE UNIQUE INDEX friendships_pair_idx ON friendships (friend1_id, friend2_id);