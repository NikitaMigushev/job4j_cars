ALTER TABLE auto_post
ADD COLUMN car_id integer REFERENCES car(id);