CREATE TABLE friendships (
    id SERIAL PRIMARY KEY,
    friend1_id int references users(id) NOT NULL,
    friend2_id int references users(id) NOT NULL,
    friends_since timestamp NOT NULL default now()
);

CREATE UNIQUE INDEX friendships_unique_pair ON friendships (LEAST(friend1_id, friend2_id), GREATEST(friend1_id, friend2_id));