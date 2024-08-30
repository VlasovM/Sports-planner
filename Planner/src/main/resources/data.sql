INSERT INTO article_status (id, title)
VALUES (1, 'Не определено'), (2, 'На проверке у модератора'), (3, 'Опубликовано'), (4, 'Отклонено')
    ON CONFLICT (id) DO UPDATE SET title = EXCLUDED.title;

INSERT INTO sports (id, title) VALUES (1, 'Бокс'), (2, 'Рукопашный бой') ON CONFLICT (id) DO NOTHING;

INSERT INTO roles (id, role) VALUES (1, 'USER'), (2, 'ADMIN') ON CONFLICT(id) DO NOTHING;

INSERT INTO users (id, name, middle_name, surname, age, birthday, sport_id)
VALUES (1, 'Леонид', 'Иванович', 'Волков', 35, '1989-04-05', 1),
       (2, 'Владимир', 'Владимирович', 'Москвин', 25, '1999-08-01', 2),
       (3, 'Елена', 'Александровна', 'Елисеева', 27, '1997-08-15', 1) ON CONFLICT (id) DO NOTHING;

INSERT INTO user_credentials (id, email, password, user_id, role_id)
VALUES (1, 'VolkovLI@planner.ru', '$2a$12$Ism/6N14n69cI0fEP9HCweNxg5lZdkpcFhxaVW106Pa0Kq1t2RJy2', 1, 1),
       (2, 'MoskvinVV@planner.ru', '$2a$12$PmPS21mmb5/goiyc4fDqROe80X1zXB0kfAHlmMmzQvbl9PWEeCPAW', 2, 1),
       (3, 'EliseevaEA@planner.ru', '$2a$12$mAmYCx.nHsD/TX8b5/u7guRy3QydIcUSrxtQL40qqa9WgPCuxFrFi', 3, 1) ON CONFLICT (id) DO NOTHING;