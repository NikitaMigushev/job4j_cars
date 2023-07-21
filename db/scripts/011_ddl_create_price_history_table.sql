create table PRICE_HISTORY (
    id serial primary key,
    price decimal(10, 2) not null,
    start_period timestamp not null,
    end_period timestamp not null,
    auto_post_id int references AUTO_POST(id) not null
);