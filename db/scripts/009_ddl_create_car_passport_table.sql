create table CAR_PASSPORT (
    id serial primary key,
    passport_number varchar(255),
    current_owner_id int references OWNER(id)
);