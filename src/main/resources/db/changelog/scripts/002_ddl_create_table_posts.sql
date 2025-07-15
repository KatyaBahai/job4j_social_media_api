create table if not exists posts (
    id BIGSERIAL primary key,
    description varchar(1000),
    heading varchar(256),
    creation_date timestamp default now(),
    user_id BIGINT not null references users(id)
);