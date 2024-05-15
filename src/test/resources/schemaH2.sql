CREATE TABLE IF NOT EXISTS sports
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS users
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    middle_name VARCHAR(32) NULL,
    surname VARCHAR(32) NOT NULL,
    age INT NOT NULL,
    birthday timestamp NOT NULL,
    sport_id INT NOT NULL,
    biography VARCHAR(2048) NULL,
    FOREIGN KEY (sport_id) REFERENCES sports(id)
);

CREATE TABLE IF NOT EXISTS user_credentials
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    email VARCHAR(32) NOT NULL,
    password VARCHAR(64) NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS article_status
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    title VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS articles
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
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
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    date timestamp NOT NULL,
    description VARCHAR(2048) NOT NULL,
    reflection VARCHAR(2048) NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS tournaments
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
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
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    date timestamp NOT NULL,
    clinic VARCHAR(32) NOT NULL,
    doctor_specialization VARCHAR(32) NOT NULL,
    doctor_full_name VARCHAR(32) NOT NULL,
    result VARCHAR(2048) NOT NULL,
    user_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
)