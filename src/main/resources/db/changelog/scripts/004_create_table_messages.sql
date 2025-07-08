create table messages (
    id serial primary key,
    sender_id int references users(id) NOT NULL,
    recipient_id int references users(id) NOT NULL,
    text varchar(10000),
    sent_at timestamp not null default now()
);