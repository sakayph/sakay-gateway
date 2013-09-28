# --- !Ups

CREATE TABLE searches (
  id serial PRIMARY KEY,
  timestamp timestamp NOT NULL DEFAULT NOW(),
  from_name text NOT NULL,
  from_latitude real,
  from_longitude real,
  to_name text NOT NULL,
  to_latitude real,
  to_longitude real
);
  
# --- !Downs

DROP TABLE searches;
