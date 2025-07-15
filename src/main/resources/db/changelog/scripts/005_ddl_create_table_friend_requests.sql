create table if not exists friend_requests (
    id BIGSERIAL primary key,
    sender_id BIGINT references users(id) NOT NULL,
    receiver_id BIGINT references users(id) NOT NULL,
    created_at timestamp not null default now(),
    status VARCHAR(255) NOT NULL
);