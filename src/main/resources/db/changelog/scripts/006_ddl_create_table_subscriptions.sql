create table if not exists subscriptions (
    id BIGSERIAL primary key,
    follower_id BIGINT references users(id) NOT NULL,
    followed_id BIGINT references users(id) NOT NULL,
    created_at timestamp default now(),
    is_subscribed boolean NOT NULL
);