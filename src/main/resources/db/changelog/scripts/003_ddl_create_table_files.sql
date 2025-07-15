create table if not exists files (
    id BIGSERIAL primary key,
    name varchar not null,
    path varchar not null unique,
    post_id BIGINT not null references posts(id)
);