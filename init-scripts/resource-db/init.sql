BEGIN;

CREATE TABLE IF NOT EXISTS resource (
    id SERIAL PRIMARY KEY,
    resource_contents bytea,
    date_created TIME
);

COMMIT;