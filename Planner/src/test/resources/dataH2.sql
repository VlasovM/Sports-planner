MERGE INTO article_status (title) KEY (title)
    VALUES ('unknown'),
           ('verification'),
           ('published'),
           ('decline');

MERGE INTO sports (title) KEY (title)
    VALUES ('sport1'),
           ('sport2');

MERGE INTO users (name, middle_name, surname, age, birthday, sport_id, biography) KEY (name, middle_name, surname, age, birthday, sport_id, biography)
    VALUES ('name1', null, 'surname1', 22, '2000-01-01 01:01:00.000', 1, 'biography1'),
           ('name2', 'middlename2', 'surname2', 25, '2000-01-01 01:01:00.000', 2, 'biography2');

MERGE INTO roles (role) KEY (role)
    VALUES ('USER'), ('ADMIN');

MERGE INTO user_credentials (email, password, user_id, role_id) KEY (email)
    VALUES ('name1@mail.ru', 'password', 1, 1), ('name2@mail.ru', 'password', 2, 1);

MERGE INTO articles (article_status, title, text, created, user_id) KEY (title, text)
    VALUES (2, 'title1', 'text1', '2000-01-01', 1),
           (3, 'title2', 'text2', '2000-01-01', 1),
           (4, 'title3', 'text3', '2000-01-01', 1);

INSERT INTO workout (date, title, reflection, user_id)
    VALUES ('2000-01-01 01:01:00.000', 'description1', 'reflection1', 1),
           ('2000-01-01 01:01:00.000', 'description2', null, 1);

MERGE INTO tournaments KEY (id, date, title, opponent, result, reflection, user_id)
    VALUES (1, '2000-01-01', 'title1', 'opponent1', 'result1', 'reflection1', 1),
           (2, '2000-01-01', 'title2', 'opponent2', 'result2', null, 1);

MERGE INTO health KEY (id, date, clinic, doctor_specialization, doctor_full_name, result, user_id, status, note)
    VALUES (1, '2000-01-01', 'clinic1', 'doctor_specialization1', 'doctor_full_name1', 'result1', 1, 'confirmed', null),
           (2, '2000-01-01', 'clinic2', 'doctor_specialization2', 'doctor_full_name2', 'result2', 1, 'unconfirmed', null);