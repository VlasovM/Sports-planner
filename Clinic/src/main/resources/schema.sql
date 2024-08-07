CREATE TABLE IF NOT EXISTS specializations
(
    id SERIAL PRIMARY KEY NOT NULL,
    specialization VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS doctors
(
    id SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(32) NOT NULL,
    middle_name VARCHAR(32) NULL,
    surname VARCHAR(32) NOT NULL,
    full_name VARCHAR(256) NOT NULL,
    specialization INT NOT NULL,
);

CREATE TABLE IF NOT EXISTS health_information
(
    id SERIAL PRIMARY KEY NOT NULL,
    request_id VARCHAR(32) NOT NULL,
    patient_name VARCHAR(64) NOT NULL,
    patient_middle_name VARCHAR(64) NULL,
    patient_surname varchar(64) NOT NULL,
    patient_birthday timestamp not null,
    visited timestamp NOT NULL,
    doctor_id INT NOT NULL,
    result VARCHAR(2048) NOT NULL,
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

