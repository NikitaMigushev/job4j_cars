create TABLE CAR_OWNER (
   id serial PRIMARY KEY,
   car_id int not null REFERENCES CAR(id),
   owner_id int not null REFERENCES OWNER(id),
   UNIQUE (car_id, owner_id)
);