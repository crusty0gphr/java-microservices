BEGIN;

CREATE TABLE IF NOT EXISTS songs (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    artist VARCHAR(100),
    album VARCHAR(100),
    duration VARCHAR(100),
    year VARCHAR(100),
    date_created TIME
);

COMMIT;