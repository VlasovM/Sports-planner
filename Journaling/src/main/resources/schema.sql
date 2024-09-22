CREATE TABLE IF NOT EXISTS logs
(
    id SERIAL PRIMARY KEY NOT NULL,
    created timestamp NOT NULL,
    action TEXT NOT NULL,
    type VARCHAR(256) NOT NULL
);