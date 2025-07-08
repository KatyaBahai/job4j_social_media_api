create table posts (
    id serial primary key,
    follower_id int references users(id) NOT NULL,
    followed_id int references users(id) NOT NULL,
    created_at timestamp not null default now(),
    is_subscribed boolean NOT NULL
);