create table AUTO_POST (
    id serial primary key,
    description text,
    created timestamp not null,
    user_id int references AUTO_USER(id) not null,
    car_id int references CAR(id) not null,
    price decimal(10, 2) not null,
    photo_id int references PHOTO(id),
    sold boolean
);