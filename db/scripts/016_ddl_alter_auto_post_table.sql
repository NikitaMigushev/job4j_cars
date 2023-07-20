ALTER TABLE auto_post
ADD COLUMN photo_id integer REFERENCES photo(id);