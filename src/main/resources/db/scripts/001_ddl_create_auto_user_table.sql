create table AUTO_USER
(
    id serial primary key,
    full_name varchar(255) not null,
    email varchar(255) not null,
    password varchar not null
);