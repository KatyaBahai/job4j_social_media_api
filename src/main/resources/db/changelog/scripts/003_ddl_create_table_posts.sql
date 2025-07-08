create table posts (
    id   serial primary key,
    description varchar(1000),
    heading varchar(256),
    creation_date timestamp not null default now(),
    user_id int not null references users(id),
);