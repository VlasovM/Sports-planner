INSERT INTO specializations (id, specialization)
VALUES (1, 'Не установлено'),
       (2, 'Терапевт'),
       (3, 'Хирург'),
       (4, 'Офтальмолог') ON CONFLICT (id) DO UPDATE SET specialization = EXCLUDED.specialization;

INSERT INTO roles (id, role) VALUES (1, 'USER'), (2, 'ADMIN') ON CONFLICT(id) DO NOTHING;

INSERT INTO doctors (id, name, middle_name, surname, full_name, specialization)
VALUES (1, 'Леонид', 'Иванович', 'Волков', 'Леонид Иванович Волков', 2),
       (2, 'Владимир', 'Владимирович', 'Москвин', 'Владимир Владимирович Москвин', 3),
       (3, 'Елена', 'Александровна', 'Елисеева', 'Елена Александровна Елисеева', 4) ON CONFLICT (id) DO NOTHING;

INSERT INTO user_credentials (id, email, password, doctor_id, role_id)
VALUES (1, 'VolkovLI@clinic.ru', '$2a$12$Ism/6N14n69cI0fEP9HCweNxg5lZdkpcFhxaVW106Pa0Kq1t2RJy2', 1, 1),
       (2, 'MoskvinVV@clinic.ru', '$2a$12$PmPS21mmb5/goiyc4fDqROe80X1zXB0kfAHlmMmzQvbl9PWEeCPAW', 2, 1),
       (3, 'EliseevaEA@clinic.ru', '$2a$12$mAmYCx.nHsD/TX8b5/u7guRy3QydIcUSrxtQL40qqa9WgPCuxFrFi', 3, 1) ON CONFLICT (id) DO NOTHING;