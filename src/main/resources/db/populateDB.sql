DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
('User', 'user@yandex.ru', 'password'),
('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
('ROLE_USER', 100000),
('ROLE_ADMIN', 100001),
('ROLE_USER', 100001);

INSERT INTO meals (user_id_m, dateTime, description, calories) VALUES
(100000, '2019-05-04 10:00:00', 'завтрак', 500),
(100000, '2019-05-04 13:00:00', 'обед', 800),
(100000, '2019-05-04 18:00:00', 'ужин', 800),
(100001, '2019-05-04 15:00:00', 'ланч', 1000);