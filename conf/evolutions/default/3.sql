# --- !Ups

CREATE TABLE pending (
  id serial PRIMARY KEY,
  target text NOT NULL,
  message text NOT NULL
);
  
# --- !Downs

DROP TABLE pending;

