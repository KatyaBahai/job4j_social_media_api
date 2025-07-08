create table friend_requests (
    id serial primary key,
    sender_id int references users(id) NOT NULL,
    receiver_id int references users(id) NOT NULL,
    created_at timestamp not null default now(),
    status VARCHAR(255) NOT NULL
);