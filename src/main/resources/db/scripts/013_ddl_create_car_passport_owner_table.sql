create TABLE CAR_PASSPORT_OWNER (
   id serial PRIMARY KEY,
   car_passport_id int not null REFERENCES CAR_PASSPORT(id),
   owner_id int not null REFERENCES OWNER(id),
   UNIQUE (car_passport_id, owner_id)
);