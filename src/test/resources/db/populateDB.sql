DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
('User', 'user@yandex.ru', 'password'),
('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
('ROLE_USER', 100000),
('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id_m, datetime, description, calories) VALUES
(100000, '2019-05-04T10:00', 'завтрак', 500),
(100000, '2019-05-04T13:00', 'обед', 800),
(100000, '2019-05-04T18:00', 'ужин', 800),
(100001, '2019-05-04T15:00', 'ланч', 1000);