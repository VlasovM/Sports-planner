CREATE TABLE IF NOT EXISTS sports
(
    id SERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(32) NOT NULL,
    middle_name VARCHAR(32) NULL,
    surname VARCHAR(32) NOT NULL,
    age INT NOT NULL,
    birthday timestamp NOT NULL,
    sport_id INT NOT NULL,
    biography VARCHAR(2048) NULL,
    FOREIGN KEY (sport_id) REFERENCES sports(id)
);

CREATE TABLE IF NOT EXISTS roles
(
    id SERIAL PRIMARY KEY NOT NULL,
    role VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_credentials
(
    id SERIAL PRIMARY KEY NOT NULL,
    email VARCHAR(32) NOT NULL,
    password VARCHAR(64) NOT NULL,
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS article_status
(
    id SERIAL PRIMARY KEY NOT NULL,
    title VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS articles
(
    id SERIAL PRIMARY KEY NOT NULL,
    article_status INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    text TEXT NOT NULL,
    created DATE NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (article_status) REFERENCES article_status(id)
);

CREATE TABLE IF NOT EXISTS trains
(
    id SERIAL PRIMARY KEY NOT NULL,
    date timestamp NOT NULL,
    title VARCHAR(256) NOT NULL,
    reflection VARCHAR(2048) NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS tournaments
(
    id SERIAL PRIMARY KEY NOT NULL,
    date timestamp NOT NULL,
    title VARCHAR(255) NOT NULL,
    opponent VARCHAR(255) NOT NULL,
    result VARCHAR(32) NOT NULL,
    reflection VARCHAR(2048) NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS health
(
    id SERIAL PRIMARY KEY NOT NULL,
    date timestamp NOT NULL,
    clinic VARCHAR(128) NOT NULL,
    doctor_specialization VARCHAR(128) NOT NULL,
    doctor_full_name VARCHAR(128) NOT NULL,
    result VARCHAR(2048) NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
)