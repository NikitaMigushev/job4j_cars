create table CAR (
    id serial primary key,
    brand_id int references BRAND(id),
    model_id int references CAR_MODEL(id),
    car_body_id int references CAR_BODY(id),
    color_id int references COLOR(id),
    car_year int,
    mileage int,
    vin varchar,
    engine_id int references ENGINE(id),
    transmission_id int references TRANSMISSION(id)
);