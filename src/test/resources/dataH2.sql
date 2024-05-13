MERGE INTO article_status (id, title) KEY (id)
    VALUES (1, 'На проверке модератором'),
           (2, 'Опубликовано');

MERGE INTO sports KEY (id, title)
    VALUES (1, 'Спорт1'),
           (2, 'Спорт2');

MERGE INTO users KEY (id, name, middle_name, surname, age, birthday, sport_id, biography)
    VALUES (1, 'Имя1', null, 'Фамилия1', 22, '2000-01-01 01:01:00.000', 1, 'Биография1'),
           (2, 'Имя2', 'Отчество2', 'Фамилия2', 25, '2000-01-01 01:01:00.000', 2, 'Биография2');

MERGE INTO articles (article_status, title, text, created, user_id) KEY (title, text)
    VALUES (1, 'Статья1', 'Текст1', '2000-01-01', 1),
           (2, 'Статья2', 'Текст2', '2000-01-01', 1);

MERGE INTO trains KEY (id, date, description, reflection, user_id)
    VALUES (1, '2000-01-01 01:01:00.000', 'Описание1', 'Рефлексия1', 1),
           (2, '2000-01-01 01:01:00.000', 'Описание2', null, 1);

MERGE INTO tournaments KEY (id, date, title, opponent, result, reflection, user_id)
    VALUES (1, '2000-01-01', 'Заголовок1', 'Соперник1', 'Результат1', 'Рефлексия1', 1),
           (2, '2000-01-01', 'Заголовок2', 'Соперник2', 'Результат1', null, 1);

MERGE INTO health KEY (id, date, clinic, doctor_specialization, doctor_full_name, result, user_id)
    VALUES (1, '2000-01-01', 'Клиника1', 'Доктор1', 'ФИО1', 'Результат1', 1),
           (2, '2000-01-01', 'Клиника2', 'Доктор2', 'ФИО2', 'Результат2', 1);