# --- !Ups

CREATE TABLE incoming (
  id serial PRIMARY KEY,
  timestamp timestamp NOT NULL DEFAULT NOW(),
  source text NOT NULL,
  message text NOT NULL,
  target text NOT NULL
);

CREATE TABLE outgoing (
  id serial PRIMARY KEY,
  timestamp timestamp NOT NULL DEFAULT NOW(),
  source text NOT NULL,
  message text NOT NULL,
  target text NOT NULL,
  reply_to integer REFERENCES incoming(id)
);

# --- !Downs

DROP TABLE outgoing;
DROP TABLE incoming;
