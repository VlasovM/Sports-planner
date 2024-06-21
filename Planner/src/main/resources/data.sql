INSERT INTO article_status (id, title)
VALUES (1, 'Не определено'), (2, 'На проверке у модератора'), (3, 'Опубликовано'), (4, 'Отклонено')
ON CONFLICT (id) DO UPDATE SET title = EXCLUDED.title;

INSERT INTO sports (title) VALUES ('Бокс'), ('Рукопашный бой') ON CONFLICT (title) DO NOTHING;

INSERT INTO roles (role) VALUES ('USER'), ('ADMIN') ON CONFLICT DO NOTHING;