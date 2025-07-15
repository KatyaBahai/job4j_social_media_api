create table if not exists messages (
    id BIGSERIAL primary key,
    sender_id BIGINT references users(id) NOT NULL,
    recipient_id BIGINT references users(id) NOT NULL,
    text varchar(10000),
    sent_at timestamp default now()
);