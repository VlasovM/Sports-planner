INSERT INTO specializations (id, title)
VALUES (1, 'Не установлено'), (2, 'Терапевт'), (3, 'Хирург'), (4, 'Офтальмолог')
ON CONFLICT (id) DO UPDATE SET title = EXCLUDED.title;