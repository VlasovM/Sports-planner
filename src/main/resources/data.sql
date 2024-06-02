INSERT INTO article_status (title)
VALUES ('Не определено'), ('На проверке модератором'), ('Опубликовано'), ('Отклонено') ON CONFLICT (title) DO NOTHING;

INSERT INTO sports (title) VALUES ('Бокс'), ('Рукопашный бой') ON CONFLICT (title) DO NOTHING;

INSERT INTO roles (role) VALUES ('USER'), ('ADMIN') ON CONFLICT DO NOTHING;