create table OWNER
(
    id        serial primary key,
    name varchar(255) not null,
    passport varchar(255) not null unique
);